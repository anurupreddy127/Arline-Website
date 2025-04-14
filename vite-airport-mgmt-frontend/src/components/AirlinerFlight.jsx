import React, { useEffect, useState } from 'react';
import axios from 'axios';

const FlightsTable = () => {
  const [flights, setFlights] = useState([]);

  useEffect(() => {
    axios.get('/api/flights')
      .then(response => {
        console.log("API RESPONSE:", response.data); 
        setFlights(response.data.flights); 
      })
      .catch(error => console.error('Error fetching flight data:', error));
  }, []);
  

  return (
    <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
      <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
      <thead>
            <tr>
                <th>Origin</th>
                <th>Destination</th>
                <th>Departure Time</th>
                <th>Arrival Time</th>
                <th>Status</th>
                <th>Airline</th>
                <th>Aircraft Name</th>
                <th>Aircraft Type</th>
                <th>Aircraft Capacity</th>
            </tr>
        </thead>

        <tbody>
            {Array.isArray(flights) && flights.map((flight) => (
                <tr key={flight.flightId}>
                <td>{flight.origin}</td>
                <td>{flight.destination}</td>
                <td>{new Date(flight.departureTime).toLocaleString()}</td>
                <td>{new Date(flight.arrivalTime).toLocaleString()}</td>
                <td>{flight.status}</td>
                <td>{flight.airline?.name || "N/A"}</td>
                <td>{flight.aircraft?.name || "N/A"}</td>
                <td>{flight.aircraft?.type || "N/A"}</td>
                <td>{flight.aircraft?.capacity || "N/A"}</td>
                </tr>
            ))}
        </tbody>

      </table>
    </div>
  );
};

export default FlightsTable;
