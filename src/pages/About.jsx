import {useState} from "react";
import Menu from "../components/Menu.jsx";

function About() {
    const [language, setLanguage] = useState('italian');


    return (
        <>
            <div className="w-100 min-vh-100 bg-black">
                <Menu language={language} setLanguage={setLanguage}/>
            </div>
        </>
    )
}
export default About;