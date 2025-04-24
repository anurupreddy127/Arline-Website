import React, {useState} from 'react'
import login_img from './../assets/Images/Login_image.jpg';
import axios from 'axios';

function Login(){
    const [user, setUser] = useState('');
    const [pwd, setPwd] = useState('');

    const login_handler = ()=>{
        fetch('http://localhost:8080/api/users/username/'+user).then(response=>response.json()).then((data)=>console.log(data));
    }
    return (
        <div style={{display: "flex", flexDirection: 'row', minHeight:'100vh'}}>
        <div style={{display:'flex', minHeight: '100vh', flex:1}}><img src={login_img} style={{ flex:1}}/></div>
        <div style={{flex:1, display:'flex', flexDirection:"column", alignItems:'center', padding:'40px'}}>
                <div style={{display: 'flex', flexDirection: "column", padding: '10px', width: '90%'}}>
                <label style={{fontSize:'28px'}}>
                    User ID:
                </label>
                <input placeholder={"Username"} style={{border:'1px solid'}} value={user} onChange={(event)=>setUser((p)=>event.target.value)}/>
                </div>
                <div style={{display: 'flex', flexDirection: "column", padding:'10px', width: '90%'}}>
                <label style={{fontSize:'28px'}}>
                    Password:
                </label>
                <input placeholder={"Password"} style={{border: '1px solid', width: '100%'}} value={pwd} onChange={(text)=>setPwd((prev)=>text.target.value)}/>
                </div>
                <button onClick={()=>login_handler()} style={{fontSize:'20px', backgroundColor: 'lightgray', cursor: 'pointer', border: '1px solid', width: '50%'}}>Log In</button>
        </div>
        </div>
    );
}

export default Login;
