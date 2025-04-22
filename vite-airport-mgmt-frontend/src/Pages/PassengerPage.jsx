import React, { useEffect, useState } from "react";

const PassengerPage = () => {
  const [passengers, setPassengers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch("http://localhost:8080/api/passengers") // your backend endpoint
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch");
        return res.json();
      })
      .then((data) => {
        setPassengers(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching passengers:", err);
        setLoading(false);
      });
  }, []);

  return (
    <div className="container mx-auto p-4">
      <h2 className="text-3xl font-semibold mb-4">Passenger List</h2>

      {loading ? (
        <p>Loading passengers...</p>
      ) : (
        <table className="min-w-full bg-white border border-gray-300 mb-8">
          <thead>
            <tr className="border-b bg-gray-100">
              <th className="p-2 text-left">Passenger ID</th>
              <th className="p-2 text-left">Name</th>
              <th className="p-2 text-left">Phone</th>
              <th className="p-2 text-left">Loyalty ID</th>
            </tr>
          </thead>
          <tbody>
            {passengers.map((passenger) => (
              <tr key={passenger.passengerId}>
                <td className="p-2">{passenger.passengerId}</td>
                <td className="p-2">{passenger.name}</td>
                <td className="p-2">{passenger.phone}</td>
                <td className="p-2">{passenger.loyaltyId}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default PassengerPage;
