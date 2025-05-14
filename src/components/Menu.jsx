import './Menu.css'
import PropTypes from "prop-types";
import {useState} from "react";
import ReactFlagsSelect from "react-flags-select";
function Menu({ language, setLanguage }) {

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

    return (
        <>
            <div className="d-flex flex-row justify-content-between p-4 text-white h4 align-items-center">
                <div className="" role="button">HistoryLine</div>
                <div className="d-flex flex-row justify-content-between gap-5 align-items-center">
                    {language === 'italian' &&
                        <>
                            <div role="button">Home</div>
                            <div role="button">Chi siamo</div>
                        </>
                    }
                    {language === 'english' &&
                        <>
                            <div role="button">Home</div>
                            <div role="button">About</div>
                        </>
                    }

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
}

export default Menu;