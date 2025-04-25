import React from 'react'
import {Link} from 'react-router-dom'
import Hp_flights from "./Hp_flights.jsx";
const HomePage = () => {

  return (
    <>
      <h1>HomePage</h1>
      <Link
              to="/airline"
              className="inline-block bg-black text-white rounded-lg px-4 py-2 hover:bg-gray-700"
            >
              Airliner Page
            </Link>
        <Hp_flights />
    </>
  )
}

export default HomePage
