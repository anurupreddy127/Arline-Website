import { useState } from 'react'
import React from 'react'
import Card from './card'
import axios from 'axios'

const AirlineCards = () => {
    const [airlineFlight, setairlineFlight] = useState('');

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
    }
    
  return (
    <section className="py-4">
      <div className="container-xl lg:container m-auto">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 p-4 rounded-lg">
            <Card > 
                <h2 className="text-2xl font-bold">Add Flight</h2>
                <div className="p-6 space-y-6">
                    <div className="grid grid-cols-6 gap-6">
                    </div>
                </div>
                <div className="p-6 space-y-6">
                    <div className="grid grid-cols-6 gap-6">
                        <div className="col-span-6 sm:col-span-3">
                            <label htmlFor="originFlight" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Origin</label>
                            <input type="text" 
                            name="originFlight" 
                            id="originFlight"
                             className="shadow-xs bg-gray-50 border border-gray-300
                             text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 
                             dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:
                             focus:border-blue-500" placeholder="Dallas" required="">
                            </input>
                        </div>
                        <div className="col-span-6 sm:col-span-3">
                            <label htmlFor="destinationFlight" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Destination</label>
                            <input type="text" 
                            name="destinationFlight" 
                            id="destinationFlight" 
                            className="shadow-xs bg-gray-50 border border-gray-300
                            text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 
                            dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:
                            border-blue-500" placeholder="Austin" required="">
                            </input>
                        </div>
                        <div className="col-span-6 sm:col-span-3">
                            <label htmlFor="departureTime" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Departure Time</label>
                            <input type="datetime-local"
                             name="departureTime" 
                             id="departureTime" 
                             className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900
                             text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
                             dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                              placeholder="YYYY-MM-DDTHH:MM" required="">
                            </input>
                        </div>
                        <div className="col-span-6 sm:col-span-3">
                            <label htmlFor="arrivalTime" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Arrival Time</label>
                            <input type="datetime-local" 
                            name="arrivalTime" 
                            id="arrivalTime" 
                            className="shadow-xs bg-gray-50 border border-gray-300 
                            text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 
                            dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" 
                            placeholder="YYYY-MM-DDTHH:MM" required="">
                            </input>
                        </div>
                        <div className="col-span-6 sm:col-span-3">
                            <label htmlFor="airline" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Airline</label>
                            <input type="text" 
                            name="airline" 
                            id="airline" 
                            className="shadow-xs bg-gray-50 border border-gray-300 
                            text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600
                             dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:
                             border-blue-500" placeholder="Spirit" required="">
                            </input>
                        </div>
                        <div className="col-span-6 sm:col-span-3">
                            <label htmlFor="Aircraft" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Aircraft</label>
                            <input type="text" 
                            name="aircraft" 
                            id="aircraft" 
                            className="shadow-xs bg-gray-50 border border-gray-300 
                            text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 
                            dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:
                            border-blue-500" placeholder="Test" required="">
                            </input>
                        </div>
                       
                        
                    </div>
                    </div>
                <div className="flex items-center p-6 space-x-3 rtl:space-x-reverse border-t border-gray-200 rounded-b 
                dark:border-gray-600">
                    <button
                    onClick={handleAddAirliner}
                    className="text-white bg-black hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-800 
                    font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:
                    ring-blue-800"
                    >
                    Add
                    </button>
                </div>
            </Card>

            <Card bg='bg-gray-300'> 
  <h2 className="text-2xl font-bold">Add Aircraft</h2>
  <div className="p-6 space-y-6">
    <div className="grid grid-cols-6 gap-6">
      <div className="col-span-6 sm:col-span-3">
        <label htmlFor="aircraftModel" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
          Model
        </label>
        <input type="text" name="aircraftModel" id="aircraftModel"
          className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
          focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
          dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="Boeing 747" required />
      </div>
      <div className="col-span-6 sm:col-span-3">
        <label htmlFor="airline" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
          Airline
        </label>
        <input type="text" name="airline" id="airline"
          className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
          focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
          dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="American Airlines" required />
      </div>
      <div className="col-span-6 sm:col-span-3">
        <label htmlFor="maintananceDate" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
          Maintenance Date
        </label>
        <input type="datetime-local" name="maintananceDate" id="maintananceDate"
          className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
          focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
          dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          required />
      </div>
      <div className="col-span-6 sm:col-span-3">
        <label htmlFor="aircraftStatus" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">
          Status
        </label>
        <input type="text" name="aircraftStatus" id="aircraftStatus"
          className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg
          focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500
          dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="Complete" required />
      </div>
    </div>
  </div>
  <div className="flex items-center p-6 space-x-3 rtl:space-x-reverse border-t border-gray-200 rounded-b dark:border-gray-600">
    <button type="submit" className="text-white bg-black hover:bg-green-800 focus:ring-4 focus:outline-none
      focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600
      dark:hover:bg-blue-700 dark:focus:ring-blue-800">
      Submit
    </button>
  </div>
</Card>

        </div>
      </div>
    </section>
  )
}

export default AirlineCards
