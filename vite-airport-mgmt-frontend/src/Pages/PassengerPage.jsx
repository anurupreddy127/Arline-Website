/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";

const PassengerPage = () => {
  const [airports, setAirports] = useState([]);
  const [origin, setOrigin] = useState("");
  const [destination, setDestination] = useState("");
  const [availableFlights, setAvailableFlights] = useState([]);
  const [bookingFlightId, setBookingFlightId] = useState(null);
  const [selectedFlight, setSelectedFlight] = useState(null);
  const [loggedInPassengerTickets, setLoggedInPassengerTickets] = useState([]);
  const [passengers, setPassengers] = useState([]);
  const [seats, setSeats] = useState([]);
  const [selectedPassenger, setSelectedPassenger] = useState("");
  const [selectedSeat, setSelectedSeat] = useState("");
  const [bookingSuccess, setBookingSuccess] = useState(false);
  const [showBookingForm, setShowBookingForm] = useState(false);
  const [ticketClasses, setTicketClasses] = useState([]);
  const [selectedClassId, setSelectedClassId] = useState("");
  const [availableLounges, setAvailableLounges] = useState([]);
  const [dependents, setDependents] = useState([]);
  const [selectedTicketBaggage, setSelectedTicketBaggage] = useState([]);
  const [selectedTicket, setSelectedTicket] = useState(null);
  const [showNewDependentForm, setShowNewDependentForm] = useState(false);
  const [newDependentData, setNewDependentData] = useState({
    name: "",
    phone: "",
  });

  const loggedInUsername = localStorage.getItem("loggedInUser");

  // Fetch airport list on mount
  useEffect(() => {
    fetch("http://localhost:8080/api/airports")
      .then((res) => res.json())
      .then((data) => setAirports(data))
      .catch((err) => console.error("Error fetching airports:", err));
  }, []);

  useEffect(() => {
    fetch("http://localhost:8080/api/lounges")
      .then((res) => res.json())
      .then((data) => setAvailableLounges(data))
      .catch((err) => console.error("Error fetching lounges:", err));
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

              // Step 2: Fetch dependents of this passenger
              fetch(
                `http://localhost:8080/api/dependents/passenger/${passengerData.passengerId}`
              )
                .then((res) => res.json())
                .then((dependentData) => setDependents(dependentData))
                .catch((err) =>
                  console.error("Error fetching dependents:", err)
                );

              // Fetch booked tickets for this passenger
              if (passengerData?.passengerId) {
                fetch(
                  `http://localhost:8080/api/tickets/passenger/${passengerData.passengerId}/detailed`
                )
                  .then((res) => res.json())
                  .then((tickets) => setLoggedInPassengerTickets(tickets))
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

  useEffect(() => {
    fetch("http://localhost:8080/api/ticket-classes")
      .then((res) => res.json())
      .then((data) => setTicketClasses(data))
      .catch((err) => console.error("Error fetching ticket classes:", err));
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

  const handleClassChange = (e) => {
    const classId = e.target.value;
    setSelectedClassId(classId);

    // Determine price based on class
    let newPrice = 500; // Default for Economy
    const selectedClass = ticketClasses.find(
      (c) => c.classId.toString() === classId.toString()
    );
    if (selectedClass) {
      if (selectedClass.name.toLowerCase() === "business") {
        newPrice = 800;
      } else if (selectedClass.name.toLowerCase() === "first class") {
        newPrice = 1500;
      }
    }

    // Update seat prices locally
    const updatedSeats = seats.map((seat) => ({
      ...seat,
      price: newPrice,
    }));

    setSeats(updatedSeats);
  };

  const handleBookTicket = async () => {
    if (
      !selectedPassenger ||
      !selectedSeat ||
      !selectedClassId ||
      !loggedInPassenger?.phone
    ) {
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
          loyaltyId: loggedInPassenger.loyaltyId || null,
        }),
      });

      // Step 2: Set price based on class selection
      let priceToUpdate = 500; // Default for Economy

      if (selectedClassId) {
        const selectedClass = ticketClasses.find(
          (c) => c.classId.toString() === selectedClassId.toString()
        );

        if (selectedClass) {
          if (selectedClass.name.toLowerCase() === "business") {
            priceToUpdate = 800;
          } else if (selectedClass.name.toLowerCase() === "first class") {
            priceToUpdate = 1500;
          }
        }
      }

      console.log("Selected Seat ID for price update:", selectedSeat);
      console.log("New Price:", priceToUpdate);

      // Step 3: Update ticket price in backend
      await fetch(
        `http://localhost:8080/api/tickets/${selectedSeat}/price?newPrice=${priceToUpdate}`,
        {
          method: "PUT",
        }
      );

      // Step 4: Book the ticket with passengerId and classId
      const res = await fetch(
        `http://localhost:8080/api/tickets/${selectedSeat}/book?passengerId=${selectedPassenger}&classId=${selectedClassId}`,
        {
          method: "POST",
        }
      );

      if (res.ok) {
        setBookingSuccess(true);
        setSelectedPassenger("");
        setSelectedSeat("");
        setSelectedClassId("");

        // Fetch updated trips
        if (loggedInPassenger?.passengerId) {
          fetch(
            `http://localhost:8080/api/tickets/passenger/${loggedInPassenger.passengerId}/detailed`
          )
            .then((res) => res.json())
            .then((tickets) => setLoggedInPassengerTickets(tickets))
            .catch((err) =>
              console.error("Error fetching updated trips:", err)
            );
        }
      } else {
        alert("Failed to book ticket.");
      }
    } catch (err) {
      console.error("Booking error:", err);
      alert("Something went wrong!");
    }
  };

  const handleAddDependent = async () => {
    if (!newDependentData.name) {
      alert("Please enter the dependent's name.");
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/api/dependents", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: newDependentData.name,
          phone: newDependentData.phone || null,
          passengerId: loggedInPassenger?.passengerId,
        }),
      });

      if (res.ok) {
        alert("Dependent added successfully!");

        // Step 1: Clear the form
        setNewDependentData({ name: "", phone: "" });
        setShowNewDependentForm(false);

        // Step 2: Re-fetch the dependents list to update dropdown
        fetch(
          `http://localhost:8080/api/dependents/passenger/${loggedInPassenger.passengerId}`
        )
          .then((res) => res.json())
          .then((data) => setDependents(data))
          .catch((err) =>
            console.error("Error fetching updated dependents:", err)
          );
      } else {
        alert("Failed to add dependent.");
      }
    } catch (err) {
      console.error("Error adding dependent:", err);
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
            {/* Passenger Selection */}
            <div>
              <label className="block mb-1 font-medium">Passenger:</label>
              <select
                className="p-2 border border-gray-400 w-full rounded"
                value={selectedPassenger}
                onChange={(e) => {
                  setSelectedPassenger(e.target.value);
                  if (e.target.value === "new-dependent") {
                    setShowNewDependentForm(true);
                  } else {
                    setShowNewDependentForm(false);
                  }
                }}
              >
                {loggedInPassenger && (
                  <option value={loggedInPassenger.passengerId}>
                    {loggedInPassenger.name} (You)
                  </option>
                )}
                {dependents.map((dep) => (
                  <option key={dep.dependentId} value={dep.passengerId}>
                    {dep.name} (Dependent)
                  </option>
                ))}
                <option value="new-dependent">+ Add New Dependent</option>
              </select>
            </div>

            {/* New Dependent Form */}
            {showNewDependentForm && (
              <div className="mt-4 p-4 bg-gray-100 rounded shadow">
                <h4 className="text-lg font-semibold mb-2">
                  Add New Dependent
                </h4>

                <input
                  type="text"
                  placeholder="Dependent Name"
                  className="p-2 border border-gray-400 w-full mb-3 rounded"
                  value={newDependentData.name}
                  onChange={(e) =>
                    setNewDependentData({
                      ...newDependentData,
                      name: e.target.value,
                    })
                  }
                />

                <input
                  type="text"
                  placeholder="Phone Number (optional)"
                  className="p-2 border border-gray-400 w-full mb-3 rounded"
                  value={newDependentData.phone}
                  onChange={(e) =>
                    setNewDependentData({
                      ...newDependentData,
                      phone: e.target.value,
                    })
                  }
                />

                <button
                  onClick={handleAddDependent}
                  className="bg-blue-600 text-white px-4 py-2 rounded"
                >
                  Save Dependent
                </button>
              </div>
            )}

            {/* Phone Number */}
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

            {/* Loyalty ID */}
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

            {/* Class Selection */}
            <div>
              <label className="block mb-1 font-medium">Select Class:</label>
              <select
                className="p-2 border border-gray-400 w-full rounded"
                value={selectedClassId}
                onChange={handleClassChange}
              >
                <option value="">Select Class</option>
                {ticketClasses.map((cls) => (
                  <option key={cls.classId} value={cls.classId}>
                    {cls.name} - {cls.benefits}
                  </option>
                ))}
              </select>
            </div>

            {/* Seat Selection */}
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

            {/* Confirm Button */}
            <button
              onClick={handleBookTicket}
              className="bg-purple-600 hover:bg-purple-700 text-white font-semibold px-6 py-2 rounded shadow"
            >
              Confirm Booking
            </button>

            {/* Success Message */}
            {bookingSuccess && (
              <p className="text-green-600 font-semibold text-center">
                Ticket booked successfully!
              </p>
            )}
          </div>
        </div>
      )}
      {loggedInPassengerTickets.length > 0 && (
        <div className="max-w-6xl mx-auto px-4 py-8 bg-white bg-opacity-90 rounded-lg shadow-md mt-10">
          <h3 className="text-4xl font-semibold mb-4 text-center">
            Your Trips
          </h3>
          <table className="w-full border border-gray-300 rounded-lg overflow-hidden">
            <thead>
              <tr className="bg-gray-100 text-center">
                <th className="p-3 w-1/4">Flight</th>
                <th className="p-3 w-1/4">Seat</th>
                <th className="p-3 w-1/4">Class</th>
                <th className="p-3 w-1/4">Price</th>
              </tr>
            </thead>
            <tbody>
              {loggedInPassengerTickets.map((ticket) => (
                <tr
                  key={ticket.ticketId}
                  className="border-t text-center cursor-pointer hover:bg-gray-100 transition"
                  onClick={() => {
                    setSelectedTicket(ticket);
                    fetch(
                      `http://localhost:8080/api/baggage/passenger/${ticket.passengerId}?flightId=${ticket.flightId}`
                    )
                      .then((res) => res.json())
                      .then((data) => setSelectedTicketBaggage(data))
                      .catch((err) =>
                        console.error("Error fetching baggage info:", err)
                      );
                  }}
                >
                  <td className="p-3 w-1/4">
                    {ticket.flightDetails || "Unknown"}
                  </td>
                  <td className="p-3 w-1/4">{ticket.seat}</td>
                  <td className="p-3 w-1/4">{ticket.className || "N/A"}</td>
                  <td className="p-3 w-1/4">${ticket.price}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
      {selectedTicket && (
        <div className="mt-10 max-w-4xl mx-auto bg-white rounded-lg shadow-md p-6 border border-gray-300">
          <h3 className="text-2xl font-semibold mb-6 text-center">
            Your Ticket
          </h3>
          <div className="grid grid-cols-2 gap-6 text-sm sm:text-base">
            <div>
              <strong>Name:</strong> {loggedInPassenger?.name}
            </div>
            <div>
              <strong>Flight:</strong> {selectedTicket.flightDetails}
            </div>
            <div>
              <strong>Seat:</strong> {selectedTicket.seat}
            </div>
            <div>
              <strong>Class:</strong> {selectedTicket.className}
            </div>
            <div>
              <strong>Price:</strong> ${selectedTicket.price}
            </div>
            <div>
              <strong>Loyalty ID:</strong>{" "}
              {loggedInPassenger?.loyaltyId || "Not Provided"}
            </div>

            <div className="col-span-2">
              <strong>Lounges:</strong>{" "}
              {availableLounges
                .filter(
                  (lounge) => lounge.loyaltyId === loggedInPassenger?.loyaltyId
                )
                .map((lounge) => lounge.location)
                .join(", ") || "No Access"}
            </div>

            <div className="col-span-2">
              <strong>Baggage Info:</strong>{" "}
              {selectedTicket.className?.toLowerCase().includes("business") ||
              selectedTicket.className?.toLowerCase().includes("first")
                ? "2 carry-on bags allowed"
                : "1 carry-on bag allowed"}
            </div>

            {selectedTicketBaggage.length > 0 && (
              <div className="col-span-2">
                <strong>Baggage Tracking:</strong>
                <ul className="list-disc ml-6 mt-2">
                  {selectedTicketBaggage.map((bag) => (
                    <li key={bag.baggageId}>
                      {bag.status} at {bag.location}
                    </li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        </div>
      )}
    </>
  );
};

export default PassengerPage;
