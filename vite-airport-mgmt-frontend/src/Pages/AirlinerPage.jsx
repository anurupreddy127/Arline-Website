import React from 'react'
import Hero from '../components/Hero'
import AirlineCards from '../components/AirlineCards'
import Airliners from '../components/Airliners'
import Airlinerflight from '../components/AirlinerFlight'
import FlightsAircrafts from '../components/FlightsAircrafts'
import AirlineAssignments from '../components/AirlineAssignments'
import EditFlights from '.../components/EditFlights'


const AirlinerPage = () => {
  return (
    <>
        <Hero title='Airline Management' subtitle='Manage flights and planes for each Airliner' />
        <AirlineCards />
        <AirlineAssignments/>
        <FlightsAircrafts />
        <EditFlights />
        <EditCrew />
        <EditMaintenanceLog />
    
    </>
  )
}

export default AirlinerPage

