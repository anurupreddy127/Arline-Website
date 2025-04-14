import React from 'react'
import Hero from '../components/Hero'
import AirlineCards from '../components/AirlineCards'
import Airliners from '../components/Airliners'
import Airlinerflight from '../components/AirlinerFlight'


const AirlinerPage = () => {
  return (
    <>
        <Hero title='Airline Management' subtitle='Manage flights and planes for each Airliner' />
        <AirlineCards />
        <Airliners />
    
    </>
  )
}

export default AirlinerPage
