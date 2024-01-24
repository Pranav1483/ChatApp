import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import Cookies from "js-cookie";

const ChatPage = () => {
    const [user, setUser] = useState(null);
    const [chat, setChat] = useState([]);
    const [message, setMessage] = useState([]);
    const authToken = Cookies.get("authToken");
    const scrollable = useRef();

    useEffect(() => {
        axios.get("http://localhost:8080/api/v1/startup", {headers: {Authorization: "Bearer " + authToken}})
            .then(response => {
                setUser(response.data);
            })
            .catch(e => {

            });
    }, [authToken]);

    useEffect(() => {
        if (!user) {
            return
        }
        const eventSource = new EventSource("http://localhost:8080/api/v1/sse/connect/" + user.username);
        eventSource.onmessage = (event) => {
            setChat(prevChat => [...prevChat, JSON.parse(event.data)]);
        }
        eventSource.onerror = (error) => {
        }
        return () => {
            eventSource.close();
        }
    }, [user, chat]);

    useEffect(() => {
        scrollable.current.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'nearest' })
    }, [])

    const sendMessage = () => {
        const messageDTO = {
            content: message,
            type: 'TXT',
            contentURL: "",
            oneTime: false,
            userFrom: user.username
        }
        setChat(prevChat => [...prevChat, messageDTO]);
        const messageRequestDTO = {
            messageDTO: messageDTO,
            toId: (user.id === 552)?553:552,
            toGroup: false
        };
        axios.post("http://localhost:8080/api/v1/sse/user", messageRequestDTO, {headers: {Authorization: "Bearer " + authToken}});
        setMessage('');
    }

    if (!user) {
        return (
            <div>LOADING</div>
        )
    }
    else {
        return (
            <div>
                <h1>{user.fname} {user.lname}</h1>
                {chat.map((item, idx) => (
                    <div key={idx} className={(user.username === item.userFrom)?"bg-green-300":"bg-slate-300"}>{item.content}</div>
                ))}
                <input placeholder='Enter Message...' value={message} onChange={e => {setMessage(e.target.value)}}/>
                <button onClick={sendMessage}>Send</button>
            </div>
        )
    }
}

export default ChatPage