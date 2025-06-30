import {useState} from "react";
import Menu from "../components/Menu.jsx";
import Search from "../components/Search/Search.jsx";
import Timeline from "../components/Timeline/Timeline.jsx";
import Description from "../components/description/Description.jsx";

function Home () {
    const [characterSelected, setCharacterSelected] = useState(null);
    const [language, setLanguage] = useState('italian');
    return (<>
        <div className="w-100 min-vh-100 bg-black">
            <div className="w-75 mx-auto">
                <Menu language={language} setLanguage={setLanguage} setCharacterSelected={setCharacterSelected} />
                <Search language={language} characterSelected={characterSelected} setCharacterSelected={setCharacterSelected}/>
                {characterSelected && characterSelected.characterDescription && <Description characterSelected={characterSelected}/>}
                {characterSelected && characterSelected.events.length > 0 && <Timeline language={language} characterSelected={characterSelected}/>}
            </div>
        </div>
    </>)
}
export default Home;