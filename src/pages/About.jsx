import {useState} from "react";
import Menu from "../components/Menu.jsx";
import springBootIcon from '../assets/icon/sb-icon.svg';
import reactIcon from '../assets/icon/react-icon.svg';
import openAIIcon from '../assets/icon/openai-icon.svg';

function About() {
    const [language, setLanguage] = useState('italian');


    return (
        <>
            <div className="w-100 min-vh-100 bg-black">
                <Menu language={language} setLanguage={setLanguage}/>
                {language === 'italian' && (
                    <div className="container">
                        <div className="row">
                            <div className="col-12 m-3 d-flex flex-column justify-content-center align-items-center text-white">
                                <div className="h2">Chi siamo</div>
                                <div className="mt-2">
                                    <p>HistoryLine è una piattaforma web in cui è possibile visionare gli eventi che hanno segnato la vita di un personaggio storico.
                                        <br/>Inserito il nome di un personaggio, l&#39;applicazione interrogherà Wikimedia per ottenerne le informazioni più importanti.
                                        <br/>Tali informazioni saranno successivamente inviate a OpenAI, che le elaborerà e fornirà come output una linea del tempo.</p>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-12 m-3 d-flex flex-column justify-content-center align-items-center text-white">
                                <div className="h2">Tecnologie utilizzate</div>
                                <div className="row mt-3">
                                    <div className="col-4">
                                        <img src={springBootIcon} alt="spring_boot_icon"></img>
                                    </div>
                                    <div className="col-4">
                                        <img src={reactIcon} alt="react_icon"></img>
                                    </div>
                                    <div className="col-4">
                                        <img src={openAIIcon} alt="open_ai_icon"></img>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                )}
                {language === 'english' && (
                    <div className="container">
                        <div className="row">
                            <div className="col-12 m-3 d-flex flex-column justify-content-center align-items-center text-white">
                                <div className="h2">About</div>
                                <div className="mt-2">
                                    <p>HistoryLine is a web platform where you can view the events that marked the life of a historical figure.
                                        <br/>Once entered a character&#39;s name, and the application will query Wikimedia to get the most important information.
                                        <br/>This information will then be sent to OpenAI, which will process it and provide a timeline as output.</p>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-12 m-3 d-flex flex-column justify-content-center align-items-center text-white">
                                <div className="h2">Dev Stack</div>
                                <div className="row mt-3">
                                    <div className="col-4">
                                        <img src={springBootIcon} alt="spring_boot_icon"></img>
                                    </div>
                                    <div className="col-4">
                                        <img src={reactIcon} alt="react_icon"></img>
                                    </div>
                                    <div className="col-4">
                                        <img src={openAIIcon} alt="open_ai_icon"></img>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </>
    )
}

export default About;