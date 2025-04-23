import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider,
} from "react-router-dom";
import React from "react";
import MainLayout from "./components/Layouts/MainLayout";
import HomePage from "./Pages/HomePage";
import AirlinerPage from "./Pages/AirlinerPage";
import PassengerPage from "./Pages/PassengerPage";
import Login from './Pages/Login';

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<MainLayout />}>
      <Route index element={<HomePage />} />
      <Route path="/airline" element={<AirlinerPage />} />
      <Route path="/passenger" element={<PassengerPage />}/>
      <Route path="/login" element={<Login />}/>
    </Route>
  )
);

const App = () => {
  return <RouterProvider router={router} />;
};

export default App;
