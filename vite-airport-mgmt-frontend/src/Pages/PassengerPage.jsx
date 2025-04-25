/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";

const PassengerPage = () => {
  const [airports, setAirports] = useState([]);
  const [origin, setOrigin] = useState("");
  const [destination, setDestination] = useState("");
  const [availableFlights, setAvailableFlights] = useState([]);
  const [bookingFlightId, setBookingFlightId] = useState(null);
  const [selectedFlight, setSelectedFlight] = useState(null);
  const [bookedTrips, setBookedTrips] = useState([]);
  const [passengers, setPassengers] = useState([]);
  const [seats, setSeats] = useState([]);
  const [selectedPassenger, setSelectedPassenger] = useState("");
  const [selectedSeat, setSelectedSeat] = useState("");
  const [bookingSuccess, setBookingSuccess] = useState(false);
  const [showBookingForm, setShowBookingForm] = useState(false);

  const loggedInUsername = localStorage.getItem("loggedInUser");

  // Fetch airport list on mount
  useEffect(() => {
    fetch("http://localhost:8080/api/airports")
      .then((res) => res.json())
      .then((data) => setAirports(data))
      .catch((err) => console.error("Error fetching airports:", err));
  }, []);

  // Fetch passengers on mount
  const [loggedInPassenger, setLoggedInPassenger] = useState(null);

  useEffect(() => {
    if (loggedInUsername) {
      fetch(`http://localhost:8080/api/users/username/${loggedInUsername}`)
        .then((res) => res.json())
        .then((userData) => {
          fetch(`http://localhost:8080/api/passengers/user/${userData.id}`)
            .then((res) => res.json())
            .then((passengerData) => {
              setLoggedInPassenger(passengerData); // Store full passenger
              setPassengers([passengerData]); // Populate dropdown if needed

              // Fetch booked tickets for this passenger
              if (passengerData?.passengerId) {
                fetch(
                  `http://localhost:8080/api/tickets/passenger/${passengerData.passengerId}`
                )
                  .then((res) => res.json())
                  .then((tickets) => setBookedTrips(tickets))
                  .catch((err) =>
                    console.error("Error fetching booked trips:", err)
                  );
              }
            })
            .catch((err) =>
              console.error("Error fetching passenger by user ID:", err)
            );
        })
        .catch((err) => console.error("Error fetching user by username:", err));
    }
  }, [loggedInUsername]);

  // Reset state when flights are updated
  useEffect(() => {
    setBookingFlightId(null);
    setShowBookingForm(false);
    setBookingSuccess(false);
    setSelectedPassenger("");
    setSelectedSeat("");
  }, [availableFlights]);

  const handleSearchFlights = async () => {
    if (!origin || !destination) return;

    try {
      const res = await fetch(
        `http://localhost:8080/api/flights/route?originAirportId=${origin}&destinationAirportId=${destination}`
      );
      const flights = await res.json();

      // For each flight, fetch available seats count
      const flightsWithSeats = await Promise.all(
        flights.map(async (flight) => {
          try {
            const seatRes = await fetch(
              `http://localhost:8080/api/tickets/flight/${flight.flightId}/available`
            );
            const seats = await seatRes.json();
            return { ...flight, availableSeats: seats.length };
          } catch (e) {
            console.error("Error fetching seats for flight", flight.flightId);
            return { ...flight, availableSeats: 0 };
          }
        })
      );

      setAvailableFlights(flightsWithSeats);
    } catch (err) {
      console.error("Error fetching flights:", err);
      setAvailableFlights([]);
    }
  };

  const handleShowBooking = (flightId) => {
    const flight = availableFlights.find((f) => f.flightId === flightId);
    setSelectedFlight(flight); // Save full flight object
    setBookingFlightId(flightId);
    setShowBookingForm(true);
    setBookingSuccess(false);

    setSelectedPassenger(loggedInPassenger?.passengerId || "");

    // Fetch available seats
    fetch(`http://localhost:8080/api/tickets/flight/${flightId}/available`)
      .then((res) => res.json())
      .then((data) => setSeats(data))
      .catch((err) => console.error("Error fetching seats:", err));
  };

  const handleBookTicket = async () => {
    if (!selectedPassenger || !selectedSeat || !loggedInPassenger?.phone) {
      alert("Please fill all required fields.");
      return;
    }

    try {
      // Step 1: Update passenger details (phone + loyalty ID)
      await fetch(`http://localhost:8080/api/passengers/${selectedPassenger}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: loggedInPassenger.name,
          phone: loggedInPassenger.phone,
          loyaltyId: loggedInPassenger.loyaltyId || null, // handle empty loyalty ID
        }),
      });

      // Step 2: Book the ticket
      const res = await fetch(
        `http://localhost:8080/api/tickets/${selectedSeat}/book?passengerId=${selectedPassenger}`,
        {
          method: "POST",
        }
      );

      if (res.ok) {
        setBookingSuccess(true);
        setSelectedPassenger("");
        setSelectedSeat("");
      } else {
        alert("Failed to book ticket.");
      }
    } catch (err) {
      console.error("Booking error:", err);
      alert("Something went wrong!");
    }
  };

  return (
    <>
      <div
        className="relative bg-cover bg-center h-[450px]"
        style={{ backgroundImage: "url('/bg.jpg')" }}
      >
        <div className="absolute inset-0 bg-black/20" />
        <div className="relative z-10 flex items-center justify-center h-full">
          <div className="bg-white/80 p-7 rounded-lg shadow-md text-center">
            <h2 className="text-4xl font-bold mb-4">Search Flights</h2>
            <div className="flex gap-4 justify-center">
              <select
                className="p-2 border border-gray-400"
                value={origin}
                onChange={(e) => setOrigin(e.target.value)}
              >
                <option value="">Select Origin</option>
                {airports.map((airport) => (
                  <option key={airport.airportId} value={airport.airportId}>
                    {airport.name}
                  </option>
                ))}
              </select>

              <select
                className="p-2 border border-gray-400"
                value={destination}
                onChange={(e) => setDestination(e.target.value)}
              >
                <option value="">Select Destination</option>
                {airports.map((airport) => (
                  <option key={airport.airportId} value={airport.airportId}>
                    {airport.name}
                  </option>
                ))}
              </select>

              <button
                onClick={handleSearchFlights}
                className="bg-blue-600 text-white px-4 py-2 rounded"
              >
                Show Available Flights
              </button>
            </div>
          </div>
        </div>
      </div>

      {bookedTrips.length > 0 && (
        <div className="max-w-6xl mx-auto px-4 py-8 bg-white bg-opacity-90 rounded-lg shadow-md mt-10">
          <h3 className="text-3xl font-semibold mb-4 text-center">
            Your Trips
          </h3>
          <table className="w-full border border-gray-300 rounded-lg overflow-hidden">
            <thead>
              <tr className="bg-gray-100 text-center">
                <th className="p-3">Flight</th>
                <th className="p-3">Seat</th>
                <th className="p-3">Class</th>
                <th className="p-3">Price</th>
              </tr>
            </thead>
            <tbody>
              {bookedTrips.map((trip) => (
                <tr key={trip.ticketId} className="border-t text-center">
                  <td className="p-3">{trip.flightDetails}</td>
                  <td className="p-3">{trip.seat}</td>
                  <td className="p-3">{trip.className}</td>
                  <td className="p-3">${trip.price}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {availableFlights.length > 0 && (
        <div className="max-w-6xl mx-auto px-4 py-8 bg-white bg-opacity-90 rounded-lg shadow-md mt-10">
          <h3 className="text-4xl font-semibold mb-4 text-center">
            Available Flights
          </h3>
          <table className="w-full border border-gray-300 rounded-lg overflow-hidden">
            <thead>
              <tr className="bg-gray-100 text-center">
                <th className="p-3">Airline</th>
                <th className="p-3">Departure</th>
                <th className="p-3">Arrival</th>
                <th className="p-3">Status</th>
                <th className="p-3">Seats Available</th>
                <th className="p-3">Action</th>
              </tr>
            </thead>
            <tbody>
              {availableFlights.map((flight) => (
                <tr key={flight.flightId} className="border-t text-center">
                  <td className="p-3">{flight.airline?.name || "Unknown"}</td>
                  <td className="p-3">{flight.departureTime}</td>
                  <td className="p-3">{flight.arrivalTime}</td>
                  <td className="p-3">{flight.status}</td>
                  <td className="p-3">{flight.availableSeats}</td>
                  <td className="p-3">
                    <button
                      onClick={() =>
                        handleShowBooking(flight.flightId, flight.airline?.name)
                      }
                      className="bg-green-600 text-white px-4 py-1 rounded hover:bg-green-700 transition"
                    >
                      Book
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showBookingForm && (
        <div className="max-w-3xl mx-auto px-4 py-8 bg-white bg-opacity-90 rounded-lg shadow-md mt-10">
          <h3 className="text-2xl font-semibold mb-6 text-center">
            Book Ticket{" "}
            {selectedFlight?.airline?.name
              ? `: ${selectedFlight.airline.name}`
              : ""}
          </h3>

          <div className="space-y-6">
            <div>
              <label className="block mb-1 font-medium">Passenger:</label>
              <select
                className="p-2 border border-gray-400 w-full rounded"
                value={selectedPassenger}
                disabled
              >
                {loggedInPassenger && (
                  <option value={loggedInPassenger.passengerId}>
                    {loggedInPassenger.name}
                  </option>
                )}
              </select>
            </div>

            <div>
              <label className="block mb-1 font-medium">Phone Number:</label>
              <input
                type="text"
                className="p-2 border border-gray-400 w-full rounded"
                value={loggedInPassenger?.phone || ""}
                onChange={(e) =>
                  setLoggedInPassenger((prev) => ({
                    ...prev,
                    phone: e.target.value,
                  }))
                }
                placeholder="Enter phone number"
              />
            </div>

            <div>
              <label className="block mb-1 font-medium">
                Loyalty ID (optional):
              </label>
              <input
                type="text"
                className="p-2 border border-gray-400 w-full rounded"
                value={loggedInPassenger?.loyaltyId || ""}
                onChange={(e) =>
                  setLoggedInPassenger((prev) => ({
                    ...prev,
                    loyaltyId: e.target.value,
                  }))
                }
                placeholder="Enter Loyalty ID"
              />
            </div>

            <div>
              <label className="block mb-1 font-medium">Select Seat:</label>
              <select
                className="p-2 border border-gray-400 w-full rounded"
                value={selectedSeat}
                onChange={(e) => setSelectedSeat(e.target.value)}
              >
                <option value="">Select Seat</option>
                {seats.map((seat) => (
                  <option key={seat.ticketId} value={seat.ticketId}>
                    {seat.seat} - ${seat.price}
                  </option>
                ))}
              </select>
            </div>

            <button
              onClick={handleBookTicket}
              className="bg-purple-600 hover:bg-purple-700 text-white font-semibold px-6 py-2 rounded shadow"
            >
              Confirm Booking
            </button>

            {bookingSuccess && (
              <p className="text-green-600 font-semibold text-center">
                Ticket booked successfully!
              </p>
            )}
          </div>
        </div>
      )}
    </>
  );
};

export default PassengerPage;
