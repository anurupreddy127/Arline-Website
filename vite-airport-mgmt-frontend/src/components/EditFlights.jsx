import { useState, useEffect } from 'react';
import React from 'react';
import Card from './card';
import axios from 'axios';


const EditFlights = () => {

    const [flightData, setFlightData] = useState({
        originAirportId: '',
        destinationAirportId: '',
        departureTime: '',
        arrivalTime: '',
        status: '',
        airlineId: '',
        aircraftId: ''
      });
    
      const [aircraftData, setAircraftData] = useState({
        name: '',
        type: '',
        capacity: '',
        airlineid: ''
      });
    
      const [airports, setAirports] = useState([]);
      const [airlines, setAirlines] = useState([]);
      const [aircrafts, setAircrafts] = useState([]);
    
      const flightFieldLabels = {
        originAirportId: 'Origin Airport',
        destinationAirportId: 'Destination Airport',
        departureTime: 'Departure Time',
        arrivalTime: 'Arrival Time',
        status: 'Status',
        airlineId: 'Airline',
        aircraftId: 'Aircraft'
      };
    
      useEffect(() => {
        const fetchData = async () => {
          try {
            const [airportRes, airlineRes, aircraftRes] = await Promise.all([
              axios.get('http://localhost:8080/api/airports'),
              axios.get('http://localhost:8080/api/airlines'),
              axios.get('http://localhost:8080/api/aircraft')
            ]);
            setAirports(airportRes.data);
            setAirlines(airlineRes.data);
            setAircrafts(aircraftRes.data);
          } catch (error) {
            console.error('Error fetching dropdown data:', error);
          }
        };
    
        fetchData();
      }, []);
    
      const handleFlightChange = (e) => {
        const { name, value } = e.target;
        const numericFields = ['originAirportId', 'destinationAirportId', 'airlineId', 'aircraftId'];
    
        const castedValue = numericFields.includes(name)
          ? value === '' ? '' : parseInt(value, 10)
          : value;
    
        console.log(`Changing field ${name} to`, castedValue);
    
        setFlightData(prev => ({
          ...prev,
          [name]: castedValue
        }));
      };
    
      const handleAddFlight = async () => {
        try {
          const payload = {
            ...flightData,
            originAirportId: flightData.originAirportId || null,
            destinationAirportId: flightData.destinationAirportId || null,
            airlineId: flightData.airlineId || null,
            aircraftId: flightData.aircraftId || null
          };
    
          console.log("Sending to backend:", payload);
          await axios.post('http://localhost:8080/api/flights', payload);
          alert("Flight added successfully!");
    
          setFlightData({
            originAirportId: '',
            destinationAirportId: '',
            departureTime: '',
            arrivalTime: '',
            status: '',
            airlineId: '',
            aircraftId: ''
          });
        } catch (error) {
          console.error("Error adding flight:", error.response || error.message);
          alert("Failed to add flight");
        }
      };
    
      const handleAircraftChange = (e) => {
        const { name, value } = e.target;
        setAircraftData(prev => ({ ...prev, [name]: value }));
      };
    
      const handleAddAircraft = async () => {
        try {
          await axios.post('http://localhost:8080/api/aircraft', aircraftData);
          alert("Aircraft added successfully!");
          setAircraftData({
            name: '',
            type: '',
            capacity: '',
            airlineid: ''
          });
        } catch (error) {
          console.error("Error adding aircraft:", error.response || error.message);
          alert("Failed to add aircraft");
        }
      };

      const [flights, setFlights] = useState([]);

      useEffect(() => {
        const fetchFlights = async () => {
          try {
            const res = await axios.get('http://localhost:8080/api/flights');
            setFlights(res.data.flights); 
          } catch (err) {
            console.error('Error fetching flights:', err);
          }
        };
        fetchFlights();
      }, []);


  return (
    <>
      <Card bg='bg-gray-100'>
  <h2 className="text-2xl font-bold">Edit Flight</h2>
  <div className="p-6 space-y-6">
    <div className="grid grid-cols-6 gap-6">
      <div className="col-span-6">
        <label htmlFor="flightId" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
          Select Flight ID
        </label>
        <select
          id="flightId"
          value={flightData.flightId || ''}
          onChange={async (e) => {
            const selectedId = parseInt(e.target.value, 10);
            try {
              const res = await axios.get(`http://localhost:8080/api/flights/${selectedId}`);
              setFlightData(res.data);
            } catch (err) {
              console.error('Error fetching flight:', err);
              alert('Failed to load flight details');
            }
          }}
          className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
            focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 
            dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        >
          <option value="">Select Flight</option>
          {flights.map((flight) => (
            <option key={flight.flightId} value={flight.flightId}>
                Flight {flight.flightId}
        </option>
))}

        </select>
      </div>

      {Object.keys(flightFieldLabels).map((field, index) => (
        <div className="col-span-6 sm:col-span-3" key={index}>
          <label htmlFor={field} className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
            {flightFieldLabels[field]}
          </label>

          {field === 'originAirportId' || field === 'destinationAirportId' ? (
            <select
              name={field}
              id={field}
              value={flightData[field] ?? ''}
              onChange={handleFlightChange}
              className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
                dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            >
              <option value="" disabled>Select {flightFieldLabels[field]}</option>
              {airports.map(airport => (
                <option key={airport.airportId} value={airport.airportId}>{airport.name}</option>
              ))}
            </select>
          ) : field === 'airlineId' ? (
            <select
              name={field}
              id={field}
              value={flightData[field] ?? ''}
              onChange={handleFlightChange}
              className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
                dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            >
              <option value="" disabled>Select Airline</option>
              {airlines.map(airline => (
                <option key={airline.airlineId} value={airline.airlineId}>{airline.name}</option>
              ))}
            </select>
          ) : field === 'aircraftId' ? (
            <select
              name={field}
              id={field}
              value={flightData[field] ?? ''}
              onChange={handleFlightChange}
              className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
                dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            >
              <option value="" disabled>Select Aircraft</option>
              {aircrafts.map(aircraft => (
                <option key={aircraft.aircraftId} value={aircraft.aircraftId}>{aircraft.name}</option>
              ))}
            </select>
          ) : (
            <input
              type={field.includes('Time') ? 'datetime-local' : 'text'}
              name={field}
              id={field}
              value={flightData[field] ?? ''}
              onChange={handleFlightChange}
              className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
                dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            />
          )}
        </div>
      ))}
    </div>
  </div>

  <div className="flex items-center p-6 space-x-3 border-t border-gray-200 rounded-b dark:border-gray-600">
    <button
      onClick={async () => {
        try {
          const updatedFlight = { ...flightData };
          await axios.put(`http://localhost:8080/api/flights/${flightData.flightId}`, updatedFlight);
          alert("Flight updated successfully!");
        } catch (err) {
          console.error("Update failed:", err.response || err.message);
          alert("Failed to update flight");
        }
      }}
      className="text-white bg-black hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-blue-800 
        font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 
        dark:focus:ring-blue-800"
    >
      Save Changes
    </button>
  </div>
</Card>

    </>
  )
}

export default EditFlights
