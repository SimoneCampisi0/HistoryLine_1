import './App.css'
import Menu from "./components/Menu.jsx";
import Search from "./components/Search.jsx";

function App() {

    return (<>
            <div className="w-100 min-vh-100 bg-black">
                <div className="w-75 mx-auto">
                    <Menu/>
                    <Search/>
                </div>
            </div>
        </>)
}

export default App;
