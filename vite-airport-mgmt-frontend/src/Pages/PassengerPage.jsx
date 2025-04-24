/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";

const PassengerPage = () => {
  const [airports, setAirports] = useState([]);
  const [origin, setOrigin] = useState("");
  const [destination, setDestination] = useState("");
  const [availableFlights, setAvailableFlights] = useState([]);
  // eslint-disable-next-line no-unused-vars
  const [bookingFlightId, setBookingFlightId] = useState(null);
  const [passengers, setPassengers] = useState([]);
  const [seats, setSeats] = useState([]);
  const [selectedPassenger, setSelectedPassenger] = useState("");
  const [selectedSeat, setSelectedSeat] = useState("");
  const [bookingSuccess, setBookingSuccess] = useState(false);
  const [showBookingForm, setShowBookingForm] = useState(false);

  // Fetch airport list on mount
  useEffect(() => {
    fetch("http://localhost:8080/api/airports")
      .then((res) => res.json())
      .then((data) => setAirports(data))
      .catch((err) => console.error("Error fetching airports:", err));
  }, []);

  // Fetch passengers on mount
  useEffect(() => {
    fetch("http://localhost:8080/api/passengers")
      .then((res) => res.json())
      .then((data) => setPassengers(data))
      .catch((err) => console.error("Error fetching passengers:", err));
  }, []);

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
    setBookingFlightId(flightId);
    setShowBookingForm(true);
    setBookingSuccess(false);

    // Fetch available seats
    fetch(`http://localhost:8080/api/tickets/flight/${flightId}/available`)
      .then((res) => res.json())
      .then((data) => setSeats(data))
      .catch((err) => console.error("Error fetching seats:", err));
  };

  const handleBookTicket = () => {
    if (!selectedPassenger || !selectedSeat) return;

    fetch(
      `http://localhost:8080/api/tickets/${selectedSeat}/book?passengerId=${selectedPassenger}`,
      {
        method: "POST",
      }
    )
      .then((res) => {
        if (res.ok) {
          setBookingSuccess(true);
          setSelectedPassenger("");
          setSelectedSeat("");
        } else {
          alert("Failed to book ticket.");
        }
      })
      .catch((err) => console.error("Booking error:", err));
  };

  return (
    <div className="container mx-auto p-4">
      <h2 className="text-2xl font-bold mb-4">Search Flights</h2>
      <div className="flex gap-4 mb-6">
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

      {availableFlights.length > 0 && (
        <>
          <h3 className="text-xl font-semibold mb-2">Available Flights</h3>
          <thead>
            <tr className="bg-gray-100">
              <th className="p-2 text-left">Airline</th>
              <th className="p-2 text-left">Departure</th>
              <th className="p-2 text-left">Arrival</th>
              <th className="p-2 text-left">Status</th>
              <th className="p-2 text-left">Seats Available</th>
              <th className="p-2 text-left">Action</th>
            </tr>
          </thead>
          <tbody>
            {availableFlights.map((flight) => (
              <tr key={flight.flightId}>
                <td className="p-2">{flight.airline?.name || "Unknown"}</td>
                <td className="p-2">{flight.departureTime}</td>
                <td className="p-2">{flight.arrivalTime}</td>
                <td className="p-2">{flight.status}</td>
                <td className="p-2">{flight.availableSeats}</td>
                <td className="p-2">
                  <button
                    onClick={() => handleShowBooking(flight.flightId)}
                    className="bg-green-600 text-white px-3 py-1 rounded"
                  >
                    Book
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </>
      )}

      {showBookingForm && (
        <div className="bg-gray-100 p-4 rounded shadow-md">
          <h3 className="text-lg font-semibold mb-2">Book Ticket</h3>
          <div className="flex gap-4 mb-4">
            <select
              className="p-2 border border-gray-400"
              value={selectedPassenger}
              onChange={(e) => setSelectedPassenger(e.target.value)}
            >
              <option value="">Select Passenger</option>
              {passengers.map((p) => (
                <option key={p.passengerId} value={p.passengerId}>
                  {p.name} ({p.passengerId})
                </option>
              ))}
            </select>

            <select
              className="p-2 border border-gray-400"
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

            <button
              onClick={handleBookTicket}
              className="bg-purple-600 text-white px-4 py-2 rounded"
            >
              Confirm Booking
            </button>
          </div>

          {bookingSuccess && (
            <p className="text-green-600 font-semibold">
              Ticket booked successfully!
            </p>
          )}
        </div>
      )}
    </div>
  );
};

export default PassengerPage;
