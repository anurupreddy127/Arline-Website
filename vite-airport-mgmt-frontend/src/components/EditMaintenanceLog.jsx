import { useState, useEffect } from 'react';
import React from 'react';
import Card from './card';
import axios from 'axios';

const EditMaintenanceLog = () => {
  const [maintenanceData, setMaintenanceData] = useState({
    logId: '',
    aircraftId: '',
    flightId: '',
    maintenanceDate: '',
    status: ''
  });

  const [aircrafts, setAircrafts] = useState([]);
  const [flights, setFlights] = useState([]);
  const [logs, setLogs] = useState([]);

  useEffect(() => {
    const fetchDropdownData = async () => {
      try {
        const [aircraftRes, flightRes, logRes] = await Promise.all([
          axios.get('http://localhost:8080/api/aircraft'),
          axios.get('http://localhost:8080/api/flights'),
          axios.get('http://localhost:8080/api/aircraft-maintenance-logs')
        ]);
        setAircrafts(aircraftRes.data);
        setFlights(flightRes.data.flights);
        setLogs(logRes.data);
      } catch (error) {
        console.error('Error fetching dropdown data:', error);
      }
    };
    fetchDropdownData();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    const numericFields = ['aircraftId', 'flightId'];
    const castedValue = numericFields.includes(name) ? (value === '' ? '' : parseInt(value, 10)) : value;
    setMaintenanceData((prev) => ({ ...prev, [name]: castedValue }));
  };

  return (
    <Card bg="bg-gray-100">
      <h2 className="text-2xl font-bold">Edit Maintenance Log</h2>
      <div className="p-6 space-y-6">
        <div className="grid grid-cols-6 gap-6">
          <div className="col-span-6">
            <label htmlFor="logId" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
              Select Log ID
            </label>
            <select
              id="logId"
              value={maintenanceData.logId || ''}
              onChange={async (e) => {
                const selectedId = parseInt(e.target.value, 10);
                try {
                  const res = await axios.get(`http://localhost:8080/api/aircraft-maintenance-logs/${selectedId}`);
                  setMaintenanceData(res.data);
                } catch (err) {
                  console.error('Error fetching log:', err);
                  alert('Failed to load log details');
                }
              }}
              className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
            >
              <option value="">Select Maintenance Log</option>
              {logs.map((log) => (
                <option key={log.logId} value={log.logId}>Log {log.logId}</option>
              ))}
            </select>
          </div>

          <div className="col-span-6 sm:col-span-3">
            <label htmlFor="aircraftId" className="block mb-2 text-sm font-medium text-gray-900">Aircraft</label>
            <select
              name="aircraftId"
              id="aircraftId"
              value={maintenanceData.aircraftId}
              onChange={handleChange}
              className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
            >
              <option value="" disabled>Select Aircraft</option>
              {aircrafts.map((ac) => (
                <option key={ac.aircraftId} value={ac.aircraftId}>{ac.name}</option>
              ))}
            </select>
          </div>

          <div className="col-span-6 sm:col-span-3">
            <label htmlFor="flightId" className="block mb-2 text-sm font-medium text-gray-900">Flight</label>
            <select
              name="flightId"
              id="flightId"
              value={maintenanceData.flightId}
              onChange={handleChange}
              className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
            >
              <option value="" disabled>Select Flight</option>
              {flights.map((flight) => (
                <option key={flight.flightId} value={flight.flightId}>Flight {flight.flightId}</option>
              ))}
            </select>
          </div>

          <div className="col-span-6 sm:col-span-3">
            <label htmlFor="maintenanceDate" className="block mb-2 text-sm font-medium text-gray-900">Date</label>
            <input
              type="datetime-local"
              name="maintenanceDate"
              id="maintenanceDate"
              value={maintenanceData.maintenanceDate}
              onChange={handleChange}
              className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
            />
          </div>

          <div className="col-span-6 sm:col-span-3">
            <label htmlFor="status" className="block mb-2 text-sm font-medium text-gray-900">Status</label>
            <input
              type="text"
              name="status"
              id="status"
              value={maintenanceData.status}
              onChange={handleChange}
              className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
            />
          </div>
        </div>
      </div>
      <div className="flex items-center p-6 space-x-3 border-t border-gray-200 rounded-b">
        <button
          onClick={async () => {
            try {
              await axios.put(`http://localhost:8080/api/aircraft-maintenance-logs/${maintenanceData.logId}`, maintenanceData);
              alert('Maintenance Log updated successfully!');
            } catch (error) {
              console.error('Error updating Maintenance Log:', error);
              alert('Failed to update Maintenance Log');
            }
          }}
          className="text-white bg-black hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-800 font-medium rounded-lg text-sm px-5 py-2.5"
        >
          Save Changes
        </button>
      </div>
    </Card>
  );
};

export default EditMaintenanceLog;

