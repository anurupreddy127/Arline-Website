import React from 'react'
import {useState} from "react";

function Hp_flights(){
    const [data, setData] = useState([]);
    fetch('http://localhost:8080/api/flights').then(d=>d.json()).then(d=>setData(d.flights))//.then(e=>console.log(data));
    console.log(data);
    return (
            <div>
                <div style={{padding: '20px', display:'flex', flexDirection:'row', width:'100%', backgroundColor:'beige'}}>
                    <label style={{flex: 1, padding:'20px', width:'20%', alignItems:'center'}}>Departure</label>
                    <label style={{flex: 1,padding:'20px', width:'20%', alignItems:'center'}}>Arrival</label>
                    <label style={{flex: 1, padding:'20px', width:'20%', alignItems:'center'}}>Destination</label>
                    <label style={{flex:1, padding:'20px', width:'20%', alignItems:'center'}}>Origin</label>
                    <label style={{flex:1, padding:'20px', width:'20%', alignItems:'center'}}>Status</label>
                </div>
                {data.map(item=>(
                    <Flight_widget flight={item}/>)
                )}
            </div>
    );
}

const get_bg_col = (status)=>{
    if(status==='ON TIME'){
        return 'cyan';
    }
    else if(status==='DELAYED'){
        return 'red';
    }
    else{
        return 'yellow';
    }
}

function Flight_widget({flight}){
    return (
        <div style={{display: "flex", flexDirection: "row", padding:'20px', width: '100%'}}>
            <label style={{padding:'20px', width:'20%'}}>{flight.departureTime}</label>
            <label style={{padding:'20px', width:'20%'}}>{flight.arrivalTime}</label>
            <label style={{padding:'20px', width:'20%'}}>{flight.destination}</label>
            <label style={{padding:'20px', width:'20%'}}>{flight.origin}</label>
            <label style={{padding:'20px', width:'20%', backgroundColor:get_bg_col(flight.status)}}>{flight.status}</label>
        </div>
    );
}

export default Hp_flights;