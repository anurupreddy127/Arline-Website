import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Card from './card';

const AirlineAssignments = () => {
  const [flightCrew, setFlightCrew] = useState({
    name: '',
    role: '',
    flightId: '',
    airlineId: '',
  });

  const [maintenanceData, setMaintenanceData] = useState({
    aircraftId: '',
    flightId: '',
    maintenanceDate: '',
    status: '',
  });

  const [aircraftData, setAircraftData] = useState({
    name: '',
    type: '',
    capacity: '',
    airlineid: '',
  });

  const [flights, setFlights] = useState([]);
  const [airlines, setAirlines] = useState([]);
  const [aircrafts, setAircrafts] = useState([]);

  const flightCrewLabel = {
    name: 'Name',
    role: 'Role',
    flightId: 'Flight',
    airlineId: 'Airline',
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [flightRes, airlineRes, aircraftRes] = await Promise.all([
          axios.get('http://localhost:8080/api/flights'),
          axios.get('http://localhost:8080/api/airlines'),
          axios.get('http://localhost:8080/api/aircraft')
        ]);

        const flightArray = Array.isArray(flightRes.data.flights) ? flightRes.data.flights : [];
        setFlights(flightArray);
        console.log('Flights:', flightRes.data);
        setAirlines(airlineRes.data);
        setAircrafts(aircraftRes.data);
      } catch (error) {
        console.error('Error fetching dropdown data:', error);
      }
    };
    fetchData();
  }, []);

  const handleAssignCrew = (e) => {
    const { name, value } = e.target;
    const numericFields = ['flightId', 'airlineId'];
    const castedValue = numericFields.includes(name) ? parseInt(value, 10) : value;
    setFlightCrew((prev) => ({ ...prev, [name]: castedValue }));
  };

  const handleNewCrew = async () => {
    try {
      await axios.post('http://localhost:8080/api/flight-crew', flightCrew);
      alert('Crew Member added successfully!');
      setFlightCrew({ name: '', role: '', flightId: '', airlineId: '' });
    } catch (error) {
      console.error('Error adding New Crew:', error);
      alert('Failed to add New Crew');
    }
  };

  const handleMaintenanceChange = (e) => {
    const { name, value } = e.target;
    const numericFields = ['aircraftId', 'flightId'];
    const castedValue = numericFields.includes(name) ? parseInt(value, 10) : value;
    setMaintenanceData((prev) => ({ ...prev, [name]: castedValue }));
  };

  const handleAddMaintenance = async () => {
    try {
      await axios.post('http://localhost:8080/api/aircraft-maintenance-logs', maintenanceData);
      alert('Maintenance Log added successfully!');
      setMaintenanceData({ aircraftId: '', flightId: '', maintenanceDate: '', status: '' });
    } catch (error) {
      console.error('Error adding Maintenance:', error);
      alert('Failed to add Maintenance');
    }
  };

  return (
    <section className="py-4">
      <div className="container-xl lg:container m-auto">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 p-4 rounded-lg">

          {/* Add Crew Member Card */}
          <Card>
            <h2 className="text-2xl font-bold">Add Crew Member</h2>
            <div className="p-6 space-y-6">
              <div className="grid grid-cols-6 gap-6">
                {Object.keys(flightCrewLabel).map((field, index) => (
                  <div className="col-span-6 sm:col-span-3" key={index}>
                    <label htmlFor={field} className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
                      {flightCrewLabel[field]}
                    </label>
                    {field === 'flightId' ? (
                      <select
                        name={field}
                        id={field}
                        value={flightCrew[field]}
                        onChange={handleAssignCrew}
                        className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
                      >
                        <option value="" disabled>Select Flight</option>
                        {Array.isArray(flights) && flights.map(flight =>
                          <option key={flight.flightId} value={flight.flightId}>
                            {flight.name || `${flight.flightId} ${flight.origin} → ${flight.destination}`}
                          </option>
                        )}
                      </select>
                    ) : field === 'airlineId' ? (
                      <select
                        name={field}
                        id={field}
                        value={flightCrew[field]}
                        onChange={handleAssignCrew}
                        className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
                      >
                        <option value="" disabled>Select Airline</option>
                        {airlines.map(airline =>
                          <option key={airline.airlineId} value={airline.airlineId}>
                            {airline.name}
                          </option>
                        )}
                      </select>
                    ) : (
                      <input
                        type="text"
                        name={field}
                        id={field}
                        value={flightCrew[field]}
                        onChange={handleAssignCrew}
                        className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
                      />
                    )}
                  </div>
                ))}
              </div>
            </div>
            <div className="flex items-center p-6 space-x-3 border-t border-gray-200 rounded-b">
              <button onClick={handleNewCrew} className="text-white bg-black hover:bg-green-800 focus:ring-4 focus:outline-none
                focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5">
                Add Crew Member
              </button>
            </div>
          </Card>

          {/* Add Maintenance Card */}
          <Card bg="bg-gray-300">
            <h2 className="text-2xl font-bold">Add Maintenance Log</h2>
            <div className="p-6 space-y-6">
              <div className="grid grid-cols-6 gap-6">
                <div className="col-span-6 sm:col-span-3">
                  <label htmlFor="aircraftId" className="block mb-2 text-sm font-medium text-gray-900">Aircraft</label>
                  <select name="aircraftId" id="aircraftId" value={maintenanceData.aircraftId} onChange={handleMaintenanceChange}
                    className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5">
                    <option value="" disabled>Select Aircraft</option>
                    {aircrafts.map(ac => <option key={ac.aircraftId} value={ac.aircraftId}>{ac.name}</option>)}
                  </select>
                </div>

                <div className="col-span-6 sm:col-span-3">
                  <label htmlFor="flightId" className="block mb-2 text-sm font-medium text-gray-900">Flight</label>
                  <select name="flightId" id="flightId" value={maintenanceData.flightId} onChange={handleMaintenanceChange} 
                    className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5">
                    <option value="" disabled>Select Flight</option>
                    {Array.isArray(flights) && flights.map(flight =>
                      <option key={flight.flightId} value={flight.flightId}>
                        {flight.name || `${flight.originAirportName} → ${flight.destinationAirportName}`}
                      </option>
                    )}
                  </select>
                </div>

                <div className="col-span-6 sm:col-span-3">
                  <label htmlFor="maintenanceDate" className="block mb-2 text-sm font-medium text-gray-900">Date</label>
                  <input type="datetime-local"
                    name="maintenanceDate" 
                    id="maintenanceDate" 
                    value={maintenanceData.maintenanceDate} 
                    onChange={handleMaintenanceChange} 
                    className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5" />
                </div>

                <div className="col-span-6 sm:col-span-3">
                  <label htmlFor="status" className="block mb-2 text-sm font-medium text-gray-900">Status</label>
                  <input type="text" 
                    name="status" 
                    id="status" 
                    value={maintenanceData.status} 
                    onChange={handleMaintenanceChange} 
                    className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5" />
                </div>
              </div>
            </div>
            <div className="flex items-center p-6 space-x-3 border-t border-gray-200 rounded-b">
              <button onClick={handleAddMaintenance} className="text-white bg-black hover:bg-green-800 focus:ring-4 
                focus:outline-none focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5">
                Add Maintenance
              </button>
            </div>
          </Card>

        </div>
      </div>
    </section>
  );
};

export default AirlineAssignments;
