import { useState } from 'react'
import React from 'react'
import Card from './card'
import axios from 'axios'

const AirlineCards = () => {
    const [airlinerName, setAirlinerName] = useState('');

    const handleAddAirliner = async () => {
      try {
        await axios.post('http://localhost:8080/api/airlines', {
          name: airlinerName
        });
        alert("Airliner added successfully!");
        setAirlinerName('');
      } catch (error) {
        console.error("Error adding airliner:", error.response || error.message);
        alert("Failed to add airliner");
      }
    }
    
  return (
    <section className="py-4">
      <div className="container-xl lg:container m-auto">
      <div className="flex flex-nowrap overflow-x-auto space-x-4">
            <Card> 
                <h2 className="text-2xl font-bold">Add Airliners</h2>
                <div className="p-6 space-y-6">
                    <div className="grid grid-cols-6 gap-6">
                    <div className="col-span-6 sm:col-span-3">
                        <label htmlFor="airliner-add" className="block mb-2 text-sm font-medium text-gray-900 dark:
                        text-white">Airliner Name</label>
                        <input
                        type="text"
                        id="airliner-add"
                        value={airlinerName}
                        onChange={(e) => setAirlinerName(e.target.value)}
                        className="shadow-xs bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:
                        ring-blue-600 focus:border-blue-600 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 
                        dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                        placeholder="i.e. American Airlines"
                        required
                        />
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
            <h2 className="text-2xl font-bold">Drop Airliners</h2>
            <div className="p-6 space-y-6">
                    <div className="grid grid-cols-6 gap-6">
                        <div className="col-span-6 sm:col-span-3">
                            <label htmlFor="airliner-name" className="block mb-2 text-sm font-medium text-gray-900 dark:
                            text-white">Airliner Name</label>
                            <input type="text" name="airliner-name" id="airliner-name" className="shadow-xs bg-gray-50 border
                             border-gray-300
                             text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5 
                             dark:bg-gray-600 
                             dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:
                             focus:border-blue-500" placeholder="i.e. American Airlines" required="">
                            </input>
                        </div>
                        
                    </div>
                </div>
                <div className="flex items-center p-6 space-x-3 rtl:space-x-reverse border-t border-gray-200 rounded-b 
                dark:border-gray-600">
                    <button type="submit" className="text-white bg-black hover:bg-green-800 focus:ring-4 focus:outline-none 
                    focus:ring-green-800 
                    font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:
                    ring-blue-800"
                    >Remove</button>
                </div>
            </Card>

            <Card bg='bg-gray-300'> 
            <h2 className="text-2xl font-bold">Add Aircraft</h2>
            <div className="p-6 space-y-6">
                    <div className="grid grid-cols-6 gap-6">
                        <div className="col-span-6 sm:col-span-3">
                            <label htmlFor="aircraft-name" className="block mb-2 text-sm font-medium text-gray-900 dark:
                            text-white">Aircraft Name</label>
                            <input type="text" name="aircraft-name" id="aircraft-name" className="shadow-xs bg-gray-50 border 
                            border-gray-300
                             text-gray-900 text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5
                              dark:bg-gray-600 
                             dark:border-gray-500 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:
                             focus:border-blue-500" placeholder="i.e. Boeing 747" required="">
                            </input>
                        </div>
                        
                    </div>
                </div>
                <div className="flex items-center p-6 space-x-3 rtl:space-x-reverse border-t border-gray-200 rounded-b 
                dark:border-gray-600">
                    <button type="submit" className="text-white bg-black hover:bg-green-800 focus:ring-4 focus:outline-none 
                    focus:ring-green-800 
                    font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:
                    ring-blue-800"
                    >Remove</button>
                </div>
            </Card>
        </div>
      </div>
    </section>
  )
}

export default AirlineCards
