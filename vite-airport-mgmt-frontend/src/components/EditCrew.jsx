import { useState, useEffect } from 'react';
import React from 'react';
import Card from './card';
import axios from 'axios';

const EditCrew = () => {
  const [crewData, setCrewData] = useState({
    name: '',
    role: '',
    flightId: ''
  });

  const [flights, setFlights] = useState([]);
  const [crewList, setCrewList] = useState([]);

  const crewFieldLabels = {
    name: 'Crew Member Name',
    role: 'Role',
    flightId: 'Assigned Flight'
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [flightRes, crewRes] = await Promise.all([
          axios.get('http://localhost:8080/api/flights'),
          axios.get('http://localhost:8080/api/flight-crew')
        ]);
        setFlights(flightRes.data.flights);
        setCrewList(crewRes.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  const handleCrewChange = (e) => {
    const { name, value } = e.target;
    setCrewData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  return (
    <>
      <Card bg='bg-gray-100'>
        <h2 className="text-2xl font-bold">Edit Crew Member</h2>
        <div className="p-6 space-y-6">
          <div className="grid grid-cols-6 gap-6">
            <div className="col-span-6">
              <label htmlFor="crewId" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                Select Crew Member ID
              </label>
              <select
                id="crewId"
                value={crewData.crewId || ''}
                onChange={async (e) => {
                  const selectedId = parseInt(e.target.value, 10);
                  try {
                    const res = await axios.get(`http://localhost:8080/api/flight-crew/${selectedId}`);
                    setCrewData(res.data);
                  } catch (err) {
                    console.error('Error fetching crew:', err);
                    alert('Failed to load crew details');
                  }
                }}
                className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                  focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 
                  dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              >
                <option value="">Select Crew Member</option>
                {crewList.map((crew) => (
                  <option key={crew.crewId} value={crew.crewId}>
                    {crew.name} (ID: {crew.crewId})
                  </option>
                ))}
              </select>
            </div>

            {Object.keys(crewFieldLabels).map((field, index) => (
              <div className="col-span-6 sm:col-span-3" key={index}>
                <label htmlFor={field} className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                  {crewFieldLabels[field]}
                </label>

                {field === 'flightId' ? (
                  <select
                    name={field}
                    id={field}
                    value={crewData[field] ?? ''}
                    onChange={handleCrewChange}
                    className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg 
                      focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
                      dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                  >
                    <option value="" disabled>Select Flight</option>
                    {flights.map(flight => (
                      <option key={flight.flightId} value={flight.flightId}>Flight {flight.flightId}</option>
                    ))}
                  </select>
                ) : (
                  <input
                    type="text"
                    name={field}
                    id={field}
                    value={crewData[field] ?? ''}
                    onChange={handleCrewChange}
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
                const updatedCrew = { ...crewData };
                await axios.put(`http://localhost:8080/api/flight-crew/${crewData.crewId}`, updatedCrew);
                alert("Crew member updated successfully!");
              } catch (err) {
                console.error("Update failed:", err.response || err.message);
                alert("Failed to update crew member");
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
  );
};

export default EditCrew;
