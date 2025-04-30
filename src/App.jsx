import './App.css'
import Menu from "./components/Menu.jsx";
import Search from "./components/Search/Search.jsx";
import Timeline from "./components/Timeline/Timeline.jsx";

function App() {

    return (<>
            <div className="w-100 min-vh-100 bg-black">
                <div className="w-75 mx-auto">
                    <Menu/>
                    <Search/>
                    <Timeline/>
                </div>
            </div>
        </>)
}

export default App;
