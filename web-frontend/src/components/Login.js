import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import userService from '../services/UserService';

const Login = () => {
    const [user, setUser] = useState({username: '', password: ''});
    const navigate = useNavigate();

    useState(() => {
        Cookies.remove('authToken');
    })

    const onSubmit = () => {
        userService.loginUser(user)
            .then(response => {
                const token = response.data;
                Cookies.set('authToken', token);
                navigate({pathname: "/chat"});
            })
            .catch(e => {
                setUser({username: e, password: ''});
            });
    }
    return (
        <div>
            <h1>Login</h1>
            <input placeholder='username' value={user.username} onChange={e => {setUser({...user, username: e.target.value})}}/>
            <input placeholder='password' value={user.password} onChange={e => {setUser({...user, password: e.target.value})}}/>
            <button onClick={onSubmit}>Login</button>
        </div>
    )
}

export default Login;