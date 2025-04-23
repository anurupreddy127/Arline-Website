import { useState } from 'react';
import React from 'react';
import Card from './card';
import axios from 'axios';

const AirlineCards = () => {
  const [airlineFlight, setairlineFlight] = useState('');

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
    model: '',
    type: '',
    capacity: '',
    airline_id: ''
  });

  const handleAddAirliner = async () => {
    try {
      await axios.post('http://localhost:8080/api/airlines', {
        name: airlineFlight
      });
      alert("Airliner added successfully!");
      setairlineFlight('');
    } catch (error) {
      console.error("Error adding airliner:", error.response || error.message);
      alert("Failed to add airliner");
    }
  };

  const handleFlightChange = (e) => {
    const { name, value } = e.target;
    setFlightData(prev => ({ ...prev, [name]: value }));
  };

  const handleAddFlight = async () => {
    try {
      await axios.post('http://localhost:8080/api/flights', flightData);
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

  return (
    <section className="py-4">
      <div className="container-xl lg:container m-auto">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 p-4 rounded-lg">

          <Card>
            <h2 className="text-2xl font-bold">Add Flight</h2>
            <div className="p-6 space-y-6">
              <div className="grid grid-cols-6 gap-6">
                {['originAirportId', 'destinationAirportId', 'departureTime', 'arrivalTime', 'status', 'airlineId', 'aircraftId'].map((field, index) => (
                  <div className="col-span-6 sm:col-span-3" key={index}>
                    <label htmlFor={field} className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                      {field}
                    </label>
                    <input
                      type={field.includes('Time') ? 'datetime-local' : 'text'}
                      name={field}
                      id={field}
                      value={flightData[field]}
                      onChange={handleFlightChange}
                      className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                      focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
                       dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                      required
                    />
                  </div>
                ))}
              </div>
            </div>
            <div className="flex items-center p-6 space-x-3 rtl:space-x-reverse border-t border-gray-200 rounded-b 
            dark:border-gray-600">
              <button
                onClick={handleAddFlight}
                className="text-white bg-black hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-800 
                font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 
                ark:focus:ring-blue-800"
              >
                Add Flight
              </button>
            </div>
          </Card>

          <Card bg='bg-gray-300'>
            <h2 className="text-2xl font-bold">Add Aircraft</h2>
            <div className="p-6 space-y-6">
              <div className="grid grid-cols-6 gap-6">
                {['name', 'type', 'capacity', 'airlineid'].map((field, index) => (
                  <div className="col-span-6 sm:col-span-3" key={index}>
                    <label htmlFor={field} className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                      {field}
                    </label>
                    <input
                      type={field === 'maintenanceDate' ? 'datetime-local' : 'text'}
                      name={field}
                      id={field}
                      value={aircraftData[field]}
                      onChange={handleAircraftChange}
                      className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                      focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
                       dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                      required
                    />
                  </div>
                ))}
              </div>
            </div>
            <div className="flex items-center p-6 space-x-3 rtl:space-x-reverse border-t border-gray-200 rounded-b 
            dark:border-gray-600">
              <button
                onClick={handleAddAircraft}
                className="text-white bg-black hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-800 
                font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 
                ark:focus:ring-blue-800"
              >
                Submit
              </button>
            </div>
          </Card>

        </div>
      </div>
    </section>
  );
};

export default AirlineCards;


