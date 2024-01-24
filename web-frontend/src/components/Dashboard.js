import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from "react-router-dom";
import Cookies from 'js-cookie';
import userService from '../services/UserService';

const Dashboard = () => {

    const [user, setUser] = useState(null);
    const [search, setSearch] = useState('');
    const [inbox, setInbox] = useState([]);
    const [reciever, setReciever] = useState(null);
    const [chats, setChats] = useState([]);
    const [message, setMessage] = useState('');
    const navigate = useNavigate();
    const authToken = Cookies.get("authToken");
    const scrollable = useRef();
    
    useEffect(() => {
        if (!authToken) {
            navigate({pathname: "/login"});
        }
        else {
            userService.getUserFromToken(authToken)
                .then(response => {
                    setUser(response.data);
                    sessionStorage.setItem('userData', response.data);
                    
                })
                .catch(e => {
                    navigate({pathname: "/login"});
                });
        }
    }, [authToken, navigate]);

    useEffect(() => {
        if (user) {
            const eventSource = userService.sseEvent(user.username);
            eventSource.onmessage = (event) => {
                const data = JSON.parse(event.data);
                userService.saveLatestMessage(user.id, reciever.id, BigInt(data.id));
                data.createdAt = new Date(data.createdAt);
                setChats(prevChats => [...prevChats, data]);
            };
            eventSource.onerror = (error) => {
            };
            return () => {
                eventSource.close();
            };
        }
    }, [user, chats]);

    useEffect(() => {
        if (reciever) {
        scrollable.current.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'nearest' });
        }
    }, [chats, reciever])

    const searchUser = () => {
        userService.getUserFromUsername(authToken, search)
            .then(response => {
                setReciever(response.data);
                userService.getUserConvo(user.id, response.data.id, authToken)
                    .then(res => {
                        const dataArray = res.data.map(item => ({
                            ...item,
                            createdAt: new Date(item.createdAt)
                        }));
                        setChats(dataArray);
                    })
                    .catch(e => {

                    });
                setSearch('');
            })
            .catch(e => {
                setSearch("No such User");
            });
    }

    const sendMessage = () => {
        const messageDTO = {
            content: message,
            type: 'TXT',
            contentURL: "",
            oneTime: false,
            userFrom: user.username,
            createdAt: new Date()
        };
        setChats(prevChats => [...prevChats, messageDTO]);
        const messageRequestDTO = {
            messageDTO: messageDTO,
            toId: reciever.id,
            toGroup: false
        };
        userService.sendMessage(messageRequestDTO, authToken);
        setMessage('');
    }

    return (
        <div className='h-screen w-screen flex'>
            {user && <div className='h-full w-80 flex-col border-r-2 border-r-slate-700'>
                <h1>{user.username}</h1>
                <div className='h-20 w-11/12 my-4'>
                    <input placeholder='Search user...' value={search} onChange={e => {setSearch(e.target.value)}}/>
                    <button onClick={searchUser}>Search</button>
                </div>
            </div>}
            {reciever && <div className='h-full flex-col' style={{width: "calc(100% - 320px)"}}>
                <div className='w-full h-10'>
                    <label>{reciever.username}</label>
                </div>
                <div className='w-full flex-col overflow-y-scroll' style={{height: "calc(100% - 100px)"}}>
                    {chats.map((item, index) => (
                        <div key={index} className='w-full my-2 p-1 flex-col'>
                            <div className='my-1'>{(user.username === item.userFrom)?"You":reciever.username} {item.createdAt.getHours()}:{item.createdAt.getMinutes()}</div>
                            <div>{item.content}</div>
                        </div>
                    ))}
                    {reciever && <div ref={scrollable}/>}
                </div>
                <div className='w-full h-10'>
                    <input className='w-9/12' placeholder='Message...' value={message} onChange={e => {setMessage(e.target.value)}}/>
                    <button onClick={sendMessage}>Send</button>
                </div>
            </div>}
        </div>
    )
}

export default Dashboard