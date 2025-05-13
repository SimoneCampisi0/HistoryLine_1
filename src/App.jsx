import './App.css'
import Menu from "./components/Menu.jsx";
import Search from "./components/Search/Search.jsx";
import Timeline from "./components/Timeline/Timeline.jsx";
import {useState} from "react";

function App() {
    const [resultEvents, setResultEvent] = useState(null);
    const [language, setLanguage] = useState('italian');
    return (<>
            <div className="w-100 min-vh-100 bg-black">
                <div className="w-75 mx-auto">
                    <Menu language={language} setLanguage={setLanguage}/>
                    <Search language={language} resultEvents={resultEvents} setResultEvent={setResultEvent}/>
                    {resultEvents && resultEvents.events.length > 0 && <Timeline language={language} resultEvents={resultEvents}/>}
                </div>
            </div>
        </>)
}

export default App;
