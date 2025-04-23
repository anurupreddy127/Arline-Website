import React, { useState, useEffect } from 'react';
import axios from 'axios';

const FlightsAircrafts = () => {
  const [viewMode, setViewMode] = useState('flights');
  const [flights, setFlights] = useState([]);
  const [aircrafts, setAircrafts] = useState([]);

  useEffect(() => {
    axios.get('/api/flights')
      .then(response => {
        setFlights(response.data.flights || []);
      })
      .catch(error => console.error('Error fetching flights:', error));
  }, []);

  useEffect(() => {
    axios.get('/api/aircrafts')
      .then(response => {
        setAircrafts(response.data.aircrafts || []);
      })
      .catch(error => console.error('Error fetching aircrafts:', error));
  }, []);

  const FlightTable = ({ flights }) => (
    <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
      <thead>
        <tr>
          <th>Flight Number</th>
          <th>Origin</th>
          <th>Destination</th>
          <th>Departure Time</th>
          <th>Arrival Time</th>
          <th>Airline</th>
          <th>Aircraft</th>
          <th>Edit</th>
        </tr>
      </thead>
      <tbody>
        {flights.map((flight, index) => (
          <tr key={index}>
            <td>{flight.flightNumber}</td>
            {/* Add other data */}
          </tr>
        ))}
      </tbody>
    </table>
  );

  const AircraftTable = ({ aircrafts }) => (
    <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
      <thead>
        <tr>
          <th>Aircraft ID</th>
          <th>Model</th>
          <th>Maintance Date</th>
          <th>Status</th>
          <th>Edit</th>
        </tr>
      </thead>
      <tbody>
        {aircrafts.map((aircraft, index) => (
          <tr key={index}>
            <td>{aircraft.id}</td>
            <td>{aircraft.model}</td>
            <td>{aircraft.capacity}</td>
            <td>{aircraft.status}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );

  return (
    <>
      <div className="flex justify-center space-x-4 mb-6">
        <button
          onClick={() => setViewMode('flights')}
          className={`px-4 py-2 rounded-lg font-semibold ${
            viewMode === 'flights' ? 'bg-green-800 text-white' : 'bg-gray-200'
          }`}
        >
          Show Flights
        </button>
        <button
          onClick={() => setViewMode('aircrafts')}
          className={`px-4 py-2 rounded-lg font-semibold ${
            viewMode === 'aircrafts' ? 'bg-green-800 text-white' : 'bg-gray-200'
          }`}
        >
          Show Aircrafts
        </button>
      </div>

      {viewMode === 'flights' ? (
        <FlightTable flights={flights} />
      ) : (
        <AircraftTable aircrafts={aircrafts} />
      )}
    </>
  );
};

export default FlightsAircrafts;
