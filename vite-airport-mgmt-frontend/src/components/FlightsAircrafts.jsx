import React, { useState, useEffect } from 'react';
import axios from 'axios';

const FlightsAircrafts = () => {
  const [flights, setFlights] = useState([]);
  const [aircrafts, setAircrafts] = useState([]);
  const [maintenanceLogs, setMaintenanceLogs] = useState([]);
  const [loading, setLoading] = useState(true);

  // Fetch flights data
  const fetchFlights = () => {
    fetch("http://localhost:8080/api/flights")
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch");
        return res.json();
      })
      .then((data) => {
        console.log("Flights API data:", data);
        setFlights(data.flights || []);
      })
      .catch((err) => {
        console.error("Error fetching Flights:", err);
        setLoading(false);
      });
  };

  // Fetch maintenance logs data
  const fetchMaintenanceLogs = () => {
    axios.get('http://localhost:8080/api/aircraft-maintenance-logs')
      .then(response => {
        setMaintenanceLogs(response.data || []);
      })
      .catch(error => console.error('Error fetching maintenance logs:', error));
  };

  // Use useEffect to load data on component mount
  useEffect(() => {
    fetchFlights();
    fetchMaintenanceLogs();
  }, []);

  return (
    <div className="container mx-auto p-4">
      <h2 className="text-3xl font-semibold mb-4">Flights</h2>
      
      <div className="flex items-center p-6 space-x-3 border-t border-gray-200 rounded-b dark:border-gray-600">
                <button
                  onClick={fetchFlights}
                  className="text-white bg-black hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-800 
                    font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 
                    dark:focus:ring-blue-800"
                >
                  Refresh
                </button>
              </div>
      
      <table className="min-w-full bg-white border border-gray-300 mb-8">
        <thead>
          <tr className="border-b bg-gray-100">
            <th className="border px-4 py-2">Flight ID</th>
            <th className="border px-4 py-2">Origin</th>
            <th className="border px-4 py-2">Destination</th>
            <th className="border px-4 py-2">Departure Time</th>
            <th className="border px-4 py-2">Arrival Time</th>
            <th className="border px-4 py-2">Airline</th>
            <th className="border px-4 py-2">Aircraft</th>
          </tr>
        </thead>
        <tbody>
          {flights.map((flight, index) => (
            <tr key={index}>
              <td className="border px-4 py-2">{flight.flightId}</td>
              <td className="border px-4 py-2">{flight.origin}</td>
              <td className="border px-4 py-2">{flight.destination}</td>
              <td className="border px-4 py-2">{flight.departureTime}</td>
              <td className="border px-4 py-2">{flight.arrivalTime}</td>
              <td className="border px-4 py-2">{flight.airline?.name}</td>
              <td className="border px-4 py-2">{flight.aircraft?.name}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <h2 className="text-3xl font-semibold mb-4">Maintenance Log</h2>
      
      <div className="flex items-center p-6 space-x-3 border-t border-gray-200 rounded-b dark:border-gray-600">
                <button
                  onClick={fetchMaintenanceLogs}
                  className="text-white bg-black hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-800 
                    font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 
                    dark:focus:ring-blue-800"
                >
                  Refresh
                </button>
              </div>

      <table className="min-w-full bg-white border border-gray-300 mb-8">
        <thead>
          <tr className="border-b bg-gray-100">
            <th className="border px-4 py-2">Log ID</th>
            <th className="border px-4 py-2">Aircraft Name</th>
            <th className="border px-4 py-2">Flight Details</th>
            <th className="border px-4 py-2">Maintenance Date</th>
            <th className="border px-4 py-2">Status</th>
          </tr>
        </thead>
        <tbody>
          {maintenanceLogs.map((log) => (
            <tr key={log.logId}>
              <td className="border px-4 py-2">{log.logId}</td>
              <td className="border px-4 py-2">{log.aircraftName}</td>
              <td className="border px-4 py-2">{log.flightDetails}</td>
              <td className="border px-4 py-2">{log.maintenanceDate}</td>
              <td className="border px-4 py-2">{log.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default FlightsAircrafts;


