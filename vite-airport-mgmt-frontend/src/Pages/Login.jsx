import React, {useState} from 'react'
import login_img from './../assets/Images/Login_image.jpg';

function Login(){
    const [user, setUser] = useState('');
    const [pwd, setPwd] = useState('');

    return (
        <div style={{display: "flex", flexDirection: 'row', minHeight:'100vh'}}>
        <div style={{display:'flex', minHeight: '100vh', flex:1}}><img src={login_img} style={{ flex:1}}/></div>
        <div style={{flex:1}}>
            <form style={{display:'flex', flexDirection:"column", alignItems:'center', padding:'40px'}}>
                <div style={{display: 'flex', flexDirection: "column", padding: '10px', width: '90%'}}>
                <label style={{fontSize:'28px'}}>
                    User ID:
                </label>
                <input placeholder={"Username"} style={{border:'1px solid'}}/>
                </div>
                <div style={{display: 'flex', flexDirection: "column", padding:'10px', width: '90%'}}>
                <label style={{fontSize:'28px'}}>
                    Password:
                </label>
                <input placeholder={"Password"} style={{border: '1px solid', width: '100%'}}/>
                </div>
                <button style={{fontSize:'20px', backgroundColor: 'lightgray', cursor: 'pointer', border: '1px solid', width: '50%'}}>Log In</button>
            </form>
        </div>
        </div>
    );
}

export default Login;
