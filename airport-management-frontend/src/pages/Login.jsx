import {useState} from "react";

function Login(){
    const [userID, setUserID] = useState('');
    const [pwd, setPwd] = useState('');
    return (
        <div className="Login" style={{textAlign:'center'}}>
            <header>Login Form</header>
            <form style={{display:'border-box'}}>
            <input style={{display:'block', margin:'auto'}}
            placeholder="User ID"
            value={userID}
            onChange={(v)=>setUserID(v.target.value)}
            />
            <input style={{display:'block', margin:'auto'}}
            placeholder="Password"
            value={pwd}
            onChange={(v)=>setPwd(v.target.value)}
            />
            <button type="submit">Log In</button>
            </form>
        </div>
    );
}

export default Login;