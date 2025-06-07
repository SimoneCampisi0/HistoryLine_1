import './Menu.css'
import PropTypes from "prop-types";
import {useState} from "react";
import ReactFlagsSelect from "react-flags-select";
import {useNavigate} from "react-router-dom";
function Menu({ language, setLanguage }) {
    const navigate = useNavigate();
    const [languageSelected, setLanguageSelected] = useState("IT");

    function onChangeLanguage(code) {
        setLanguageSelected(code)
        switch (code) {
            case "IT":
                setLanguage('italian');
                break;
            case "GB":
                setLanguage('english');
                break;
        }
    }

    function goTo(path) {
        navigate(path);
    }

    return (
        <>
            <div className="container d-flex flex-row justify-content-between align-items-center p-4 text-white h4">
                <div className="" role="button">HistoryLine</div>
                <div className="d-flex flex-row align-items-center gap-4">
                    <div className="d-none d-md-flex flex-row gap-4">
                        {language === 'italian' &&
                            <>
                                <div onClick={() => goTo('/')} role="button">Home</div>
                                <div onClick={() => goTo('/about')} role="button">Chi siamo</div>
                            </>
                        }
                        {language === 'english' &&
                            <>
                                <div onClick={() => goTo('/')} role="button">Home</div>
                                <div onClick={() => goTo('/about')} role="button">About</div>
                            </>
                        }
                    </div>

                    <ReactFlagsSelect
                        selectButtonClassName="custom-react-flag"
                        selected={languageSelected}
                        onSelect={onChangeLanguage}
                        countries={["IT", "GB"]}
                        showSelectedLabel={false}
                        showSecondarySelectedLabel={false}
                        showOptionLabel={false}
                        fullWidth={false}
                    />

                </div>
            </div>

        </>
    )
}

Menu.propTypes = {
    language: PropTypes.string.isRequired,
    setLanguage: PropTypes.func.isRequired,
}

export default Menu;