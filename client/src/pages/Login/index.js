import React,{useState} from "react";
import { useNavigate } from 'react-router-dom';
import api from '../../services/api';
import './styles.css';
import logoImage from '../../assets/logo.svg';
import padlock from '../../assets/padlock.png';

export default function Login(){

    const[username, setUsername] = useState('');
    const[password, setpassword] = useState('');

    const history = useNavigate();

    async function login(e){
        e.preventDefault();

        const data = {
            username,
            password,
        };

        try {
            const response = await api.post('auth/signin', data);
            localStorage.setItem('username', username);
            localStorage.setItem('accessToken', response.data.accessToken);
            history('/books')
        } catch (err) {
            
            alert('Login failed try agains');
        }
    };

    return(
           <div className="login-container">
                <section className="form">
                <img src = {logoImage} alt = "Igor Logo"/>
                <form on onSubmit={login}>
                    <h1>Access your Account</h1>
                    <input 
                    placeholder ="Username"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                    />
                    <input type = "password"
                    placeholder ="Password"
                    value={password}
                    onChange={e => setpassword(e.target.value)}
                    />

                    <button className="button"type = "submit">Login</button>
                </form>

                </section>
                <img src = {padlock} alt = "Login"/>
           </div>
    )
}