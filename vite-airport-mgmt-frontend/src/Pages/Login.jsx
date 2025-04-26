/* eslint-disable no-unused-vars */
import React, { useState } from "react";
import login_img from "./../assets/Images/Login_image.jpg";
import authentication from "../authentication.js";

function Login() {
  const [user, setUser] = useState("");
  const [pwd, setPwd] = useState("");

  const login_handler = () => {
    fetch("http://localhost:8080/api/users/username/" + user)
      .then((response) => response.json())
      .then((data) => {
        if (data.password === pwd) {
          authentication.auth = data.role;
          localStorage.setItem("loggedInUser", user); // <<< THIS IS IMPORTANT
          alert("Login Successful");
          // Optionally redirect to /passenger if needed
          // window.location.href = "/passenger";
        } else {
          alert("Login Failed");
        }
      })
      .catch((err) => {
        console.error("Login error:", err);
        alert("Login Failed");
      });
  };

  const add_user_handler = () => {
    let len = 0;
    fetch("http://localhost:8080/api/users")
      .then((data) => data.json())
      .then((data) => {
        len = data.length + 1;
      });
    const postData = {
      id: len,
      username: user.toString(),
      password: pwd.toString(),
      role: "PASSENGER",
    };
    const options = {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(postData),
    };
    fetch("http://localhost:8080/api/users", options)
      .then((response) => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then((data) => {
        console.log("Success:", data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  };
  return (
    <div style={{ display: "flex", flexDirection: "row", minHeight: "100vh" }}>
      <div style={{ display: "flex", minHeight: "100vh", flex: 1 }}>
        <img src={login_img} style={{ flex: 1 }} />
      </div>
      <div
        style={{
          flex: 1,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          padding: "40px",
          backgroundColor: "beige",
        }}
      >
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            padding: "10px",
            width: "90%",
          }}
        >
          <label style={{ fontSize: "28px" }}>User ID:</label>
          <input
            placeholder={"Username"}
            style={{ border: "1px solid" }}
            value={user}
            onChange={(event) => setUser((p) => event.target.value)}
          />
        </div>
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            padding: "10px",
            width: "90%",
          }}
        >
          <label style={{ fontSize: "28px" }}>Password:</label>
          <input
            placeholder={"Password"}
            style={{ border: "1px solid", width: "100%" }}
            value={pwd}
            onChange={(text) => setPwd((prev) => text.target.value)}
          />
        </div>
        <button
          onClick={() => login_handler()}
          style={{
            fontSize: "20px",
            backgroundColor: "lightgray",
            cursor: "pointer",
            border: "1px solid",
            width: "50%",
          }}
        >
          Log In
        </button>
        <button
          onClick={() => add_user_handler()}
          style={{
            fontSize: "20px",
            backgroundColor: "lightgray",
            cursor: "pointer",
            border: "1px solid",
            width: "50%",
          }}
        >
          Add User
        </button>
      </div>
    </div>
  );
}

export default Login;
