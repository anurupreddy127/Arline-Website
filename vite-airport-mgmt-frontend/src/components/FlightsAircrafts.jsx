import React, { useState, useEffect } from 'react';
import axios from 'axios';

const FlightsAircrafts = () => {
  const [flights, setFlights] = useState([]);
  const [aircrafts, setAircrafts] = useState([]);
  const [maintenanceLogs, setMaintenanceLogs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [flightCrew, setFlightCrew] = useState([]);
  const [airports, setAirports] = useState([]);

  const fetchFlights = () => {
    fetch("http://localhost:8080/api/flights")
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch");
        return res.json();
      })
      .then((data) => {
        console.log("Flights API data:", data);
        setFlights(data.flights || []);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching Flights:", err);
        setLoading(false);
      });
  };

  
  const fetchAircrafts = () => {
    axios.get('http://localhost:8080/api/aircrafts')
      .then(response => {
        setAircrafts(response.data || []);
      })
      .catch(error => console.error('Error fetching aircrafts:', error));
  };

  
  const fetchMaintenanceLogs = () => {
    axios.get('http://localhost:8080/api/aircraft-maintenance-logs')
      .then(response => {
        setMaintenanceLogs(response.data || []);
      })
      .catch(error => console.error('Error fetching maintenance logs:', error));
  };

  
  const fetchFlightCrew = () => {
    axios.get('http://localhost:8080/api/flight-crew')
      .then(response => {
        setFlightCrew(response.data || []);
      })
      .catch(error => console.error('Error fetching flight crew:', error));
  };

  const fetchAirports = () => {
    axios.get('http://localhost:8080/api/airport')
      .then(response => {
        setAirports(response.data || []);
      })
      .catch(error => console.error('Error fetching airports:', error));
  };

  useEffect(() => {
    fetchFlights();
    fetchAircrafts();
    fetchMaintenanceLogs();
    fetchFlightCrew(); 
  }, []);

  const getAircraftNameById = (id) => {
    return aircrafts.find(ac => ac.aircraftId === id)?.name || 'N/A';
  };

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
              <td className="border px-4 py-2">{flight.aircraft?.name || getAircraftNameById(flight.aircraftId)}</td>
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
            <th className="border px-4 py-2">Aircraft</th>
            <th className="border px-4 py-2">Flight</th>
            <th className="border px-4 py-2">Maintenance Date</th>
            <th className="border px-4 py-2">Status</th>
          </tr>
        </thead>
        <tbody>
          {maintenanceLogs.map((log) => (
            <tr key={log.logId}>
              <td className="border px-4 py-2">{log.logId}</td>
              <td className="border px-4 py-2">{log.aircraft?.name || getAircraftNameById(log.aircraftId)}</td>
              <td className="border px-4 py-2">{log.flight?.flightId || log.flightId}</td>
              <td className="border px-4 py-2">{log.maintenanceDate}</td>
              <td className="border px-4 py-2">{log.status}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <h2 className="text-3xl font-semibold mb-4">Crew Log</h2>
      <div className="flex items-center p-6 space-x-3 border-t border-gray-200 rounded-b dark:border-gray-600">
        <button
          onClick={fetchFlightCrew}
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
      <th className="border px-4 py-2">Crew Member ID</th>
      <th className="border px-4 py-2">Name</th>
      <th className="border px-4 py-2">Role</th>
      <th className="border px-4 py-2">Flight</th>
      <th className="border px-4 py-2">Airline</th>
    </tr>
  </thead>
  <tbody>
    {flightCrew.map((crew) => {
      const flight = flights.find(f => f.flightId === crew.flightId);
      return (
        <tr key={crew.crewId}>
          <td className="border px-4 py-2">{crew.crewId}</td>
          <td className="border px-4 py-2">{crew.name}</td>
          <td className="border px-4 py-2">{crew.role}</td>
          <td className="border px-4 py-2">
            {flight ? `${flight.flightId} (${flight.origin} - ${flight.destination})` : 'N/A'}
          </td>
          <td className="border px-4 py-2">{flight?.airline?.name || 'N/A'}</td>
        </tr>
      );
    })}
  </tbody>
</table>


    </div>
  );
};

export default FlightsAircrafts;



