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
import TSAPage from "./Pages/TSAPage";
import CrewPage from "./Pages/CrewPage"

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<MainLayout />}>
      <Route index element={<HomePage />} />
      <Route path="/airline" element={<AirlinerPage />} />
      <Route path="/passenger" element={<PassengerPage />}/>
      <Route path="/tsa" element={<TSAPage />}/>
      <Route path="/crew" element={<CrewPage />}/>
        <Route path='/login' element={<Login />}/>
      
    </Route>
  )
);

const App = () => {
  return <RouterProvider router={router} />;
};

export default App;
