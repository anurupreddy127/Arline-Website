import React, { useEffect, useState } from "react";
import { getTSAPersonnel } from "../api/tsaAPI";

const TSAPage = () => {
  const [tsaPersonnel, setTSAPersonnel] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterCheckpoint, setFilterCheckpoint] = useState("all");

  useEffect(() => {
    getTSAPersonnel()
      .then((data) => {
        setTSAPersonnel(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching TSA personnel:", err);
        setLoading(false);
      });
  }, []);

  // Handle checkpoint filter change
  const handleFilterChange = (e) => {
    setFilterCheckpoint(e.target.value);
  };

  // Filter TSA personnel based on checkpoint number
  const filteredPersonnel = filterCheckpoint === "all"
    ? tsaPersonnel
    : tsaPersonnel.filter((person) => person.checkpointNumber === parseInt(filterCheckpoint));

  return (
    <div className="container mx-auto p-4">
      <h2 className="text-3xl font-semibold mb-4">TSA Personnel</h2>

      {/* Checkpoint Filter */}
      <div className="mb-4">
        <label htmlFor="checkpointFilter" className="mr-2">Filter by Checkpoint:</label>
        <select
          id="checkpointFilter"
          value={filterCheckpoint}
          onChange={handleFilterChange}
          className="p-2 border border-gray-300 rounded"
        >
          <option value="all">All Checkpoints</option>
          <option value="1">Checkpoint 1</option>
          <option value="2">Checkpoint 2</option>
          <option value="3">Checkpoint 3</option>
          <option value="4">Checkpoint 4</option>
        </select>
      </div>

      {loading ? (
        <p>Loading TSA personnel...</p>
      ) : (
        <table className="min-w-full bg-white border border-gray-300 mb-8">
          <thead>
            <tr className="border-b bg-gray-100">
              <th className="p-2 text-left">Personal ID</th>
              <th className="p-2 text-left">Name</th>
              <th className="p-2 text-left">Clearance Level</th>
              <th className="p-2 text-left">Checkpoint Number</th>
            </tr>
          </thead>
          <tbody>
            {filteredPersonnel.length > 0 ? (
              filteredPersonnel.map((person) => (
                <tr key={person.personalId}>
                  <td className="p-2">{person.personalId}</td>
                  <td className="p-2">{person.name}</td>
                  <td className="p-2">{person.clearanceLevel}</td>
                  <td className="p-2">{person.checkpointNumber}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="4" className="p-2 text-center">No personnel found for this checkpoint.</td>
              </tr>
            )}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default TSAPage;