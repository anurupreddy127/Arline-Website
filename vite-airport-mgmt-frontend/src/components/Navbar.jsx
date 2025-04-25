import React from 'react'
import icon from '../assets/Images/icon.png'
import {NavLink} from 'react-router-dom'

const Navbar = () => {
    const linkclass = ({isActive}) => isActive ? ' bg-green-800 text-white hover:bg-gray-400 hover:text-white rounded-md px-3 py-2':
                  'text-white hover:bg-gray-900 hover:text-white rounded-md px-3 py-2'

  return (
    <nav className="bg-black border-b border-gray-800">
      <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
        <div className="flex h-20 items-center justify-between">
          <div
            className="flex flex-1 items-center justify-center md:items-stretch md:justify-start"
          >
            <NavLink className="flex flex-shrink-0 items-center mr-4" to="/">
              <img
                className="h-10 w-auto"
                src={icon}
                alt="LoneStar Executive Airport"
              />
              <span className="hidden md:block text-white text-2xl font-bold ml-2"
                >Airport Name</span
              >
            </NavLink>
            <div className="md:ml-auto">
              <div className="flex space-x-2">
                <NavLink
                  to="/"
                  className={linkclass}
                  >Home</NavLink
                >
                <NavLink
                  to="/airline"
                  className={linkclass}
                  >Airline</NavLink
                >
                <NavLink
                  to="/passenger"
                  className={linkclass}
                  >Passengers</NavLink
                >
                <NavLink
                  to="/crew"
                  className={linkclass}
                  >Crew/TSA</NavLink
                >
                <NavLink
                  to="/tsa"
                  className={linkclass}
                  >Crew/TSA</NavLink
                >
                <NavLink
                  to="/login"
                  className={linkclass}
                  >Login</NavLink
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
  )
}

export default Navbar
