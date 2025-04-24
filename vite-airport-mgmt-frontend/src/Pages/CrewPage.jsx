import React, { useEffect, useState } from "react";
import { getCrewMembers } from "../api/api";

const CrewPage = () => {
  const [crewMembers, setCrewMembers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterRole, setFilterRole] = useState("all");

  useEffect(() => {
    getCrewMembers()
      .then((data) => {
        setCrewMembers(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching crew members:", err);
        setLoading(false);
      });
  }, []);

  // Handle role filter change
  const handleFilterChange = (e) => {
    setFilterRole(e.target.value);
  };

  // Filter crew members based on role
  const filteredCrew = filterRole === "all"
    ? crewMembers
    : crewMembers.filter((crew) => crew.role === filterRole);

  return (
    <div className="container mx-auto p-4">
      <h2 className="text-3xl font-semibold mb-4">Flight Crew</h2>

      {/* Role Filter */}
      <div className="mb-4">
        <label htmlFor="roleFilter" className="mr-2">Filter by Role:</label>
        <select
          id="roleFilter"
          value={filterRole}
          onChange={handleFilterChange}
          className="p-2 border border-gray-300 rounded"
        >
          <option value="all">All Roles</option>
          <option value="Pilot">Pilot</option>
          <option value="Flight Attendant">Flight Attendant</option>
        </select>
      </div>

      {loading ? (
        <p>Loading crew members...</p>
      ) : (
        <table className="min-w-full bg-white border border-gray-300 mb-8">
          <thead>
            <tr className="border-b bg-gray-100">
              <th className="p-2 text-left">Crew ID</th>
              <th className="p-2 text-left">Name</th>
              <th className="p-2 text-left">Role</th>
              <th className="p-2 text-left">Flight Details</th>
              <th className="p-2 text-left">Airline</th>
            </tr>
          </thead>
          <tbody>
            {filteredCrew.length > 0 ? (
              filteredCrew.map((crew) => (
                <tr key={crew.crewId}>
                  <td className="p-2">{crew.crewId}</td>
                  <td className="p-2">{crew.name}</td>
                  <td className="p-2">{crew.role}</td>
                  <td className="p-2">{crew.flightDetails}</td>
                  <td className="p-2">{crew.airlineName}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="5" className="p-2 text-center">No crew members found for this role.</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default CrewPage;