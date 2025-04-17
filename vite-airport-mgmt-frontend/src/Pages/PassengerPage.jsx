import React, { useState } from "react";

const PassengerPage = () => {
  // Sample data for upcoming flights
  const upcomingFlights = [
    {
      flightID: 101,
      flight: "Flight 101",
      departure: "2025-04-20 14:00",
      origin: "Dallas",
      destination: "New York",
    },
    {
      flightID: 102,
      flight: "Flight 102",
      departure: "2025-04-22 10:30",
      origin: "Dallas",
      destination: "San Francisco",
    },
    {
      flightID: 103,
      flight: "Flight 103",
      departure: "2025-04-25 16:45",
      origin: "Dallas",
      destination: "Chicago",
    },
  ];

  // eslint-disable-next-line no-unused-vars
  const [passengers, setPassengers] = useState([
    {
      passengerID: 1,
      name: "John Doe",
      phone: "123-456-7890",
      loyaltyID: 101,
      bookings: [
        {
          bookingID: 1001,
          flight: "Flight 101",
          seat: "A12",
          price: "$500",
          status: "Confirmed",
        },
        {
          bookingID: 1002,
          flight: "Flight 102",
          seat: "B15",
          price: "$450",
          status: "Pending",
        },
      ],
      history: [
        {
          historyID: 1,
          flight: "Flight 99",
          departure: "2025-01-15 08:00",
          origin: "Dallas",
          destination: "Los Angeles",
          status: "Completed",
        },
        {
          historyID: 2,
          flight: "Flight 98",
          departure: "2024-12-20 12:30",
          origin: "Dallas",
          destination: "Miami",
          status: "Completed",
        },
      ],
    },
    {
      passengerID: 2,
      name: "Jane Smith",
      phone: "234-567-8901",
      loyaltyID: 102,
      bookings: [
        {
          bookingID: 1003,
          flight: "Flight 103",
          seat: "C5",
          price: "$300",
          status: "Cancelled",
        },
      ],
      history: [
        {
          historyID: 1,
          flight: "Flight 97",
          departure: "2025-02-10 09:15",
          origin: "Dallas",
          destination: "Boston",
          status: "Completed",
        },
      ],
    },
  ]);

  return (
    <div className="container mx-auto p-4">
      {/* Next Upcoming Flights Section */}
      <div className="mb-8">
        <h2 className="text-3xl font-semibold mb-4">Next Upcoming Flights</h2>
        <table className="min-w-full bg-white border border-gray-300 mb-8">
          <thead>
            <tr className="border-b bg-gray-100">
              <th className="p-2 text-left">Flight ID</th>
              <th className="p-2 text-left">Flight</th>
              <th className="p-2 text-left">Departure</th>
              <th className="p-2 text-left">Origin</th>
              <th className="p-2 text-left">Destination</th>
            </tr>
          </thead>
          <tbody>
            {upcomingFlights.map((flight) => (
              <tr key={flight.flightID}>
                <td className="p-2">{flight.flightID}</td>
                <td className="p-2">{flight.flight}</td>
                <td className="p-2">{flight.departure}</td>
                <td className="p-2">{flight.origin}</td>
                <td className="p-2">{flight.destination}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Passenger List Section */}
      <h2 className="text-3xl font-semibold mb-4">Passenger List</h2>
      <table className="min-w-full bg-white border border-gray-300 mb-8">
        <thead>
          <tr className="border-b bg-gray-100">
            <th className="p-2 text-left">Passenger ID</th>
            <th className="p-2 text-left">Name</th>
            <th className="p-2 text-left">Phone</th>
            <th className="p-2 text-left">Loyalty ID</th>
            <th className="p-2 text-left">Bookings</th>
          </tr>
        </thead>
        <tbody>
          {passengers.map((passenger) => (
            <tr key={passenger.passengerID}>
              <td className="p-2">{passenger.passengerID}</td>
              <td className="p-2">{passenger.name}</td>
              <td className="p-2">{passenger.phone}</td>
              <td className="p-2">{passenger.loyaltyID}</td>
              <td className="p-2">
                <ul>
                  {passenger.bookings.map((booking) => (
                    <li key={booking.bookingID}>
                      <strong>Booking ID:</strong> {booking.bookingID} <br />
                      <strong>Flight:</strong> {booking.flight} <br />
                      <strong>Seat:</strong> {booking.seat} <br />
                      <strong>Price:</strong> {booking.price} <br />
                      <strong>Status:</strong> {booking.status} <br />
                    </li>
                  ))}
                </ul>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* History Section */}
      <div>
        <h2 className="text-3xl font-semibold mb-4">History</h2>
        <table className="min-w-full bg-white border border-gray-300">
          <thead>
            <tr className="border-b bg-gray-100">
              <th className="p-2 text-left">History ID</th>
              <th className="p-2 text-left">Flight</th>
              <th className="p-2 text-left">Departure</th>
              <th className="p-2 text-left">Origin</th>
              <th className="p-2 text-left">Destination</th>
              <th className="p-2 text-left">Status</th>
            </tr>
          </thead>
          <tbody>
            {passengers.map((passenger) =>
              passenger.history.map((history) => (
                <tr key={history.historyID}>
                  <td className="p-2">{history.historyID}</td>
                  <td className="p-2">{history.flight}</td>
                  <td className="p-2">{history.departure}</td>
                  <td className="p-2">{history.origin}</td>
                  <td className="p-2">{history.destination}</td>
                  <td className="p-2">{history.status}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default PassengerPage;
