import './Menu.css'
import PropTypes from "prop-types";
import {useState} from "react";
import ReactFlagsSelect from "react-flags-select";
import {useNavigate} from "react-router-dom";
import { Offcanvas } from 'bootstrap';

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
        const element = document.getElementById('mobileMenu');
        const offcanvas = Offcanvas.getInstance(element);
        offcanvas?.hide();
    }

    return (
        <>
            <div className="container p-4 text-white h4">
                <div className="row d-flex justify-content-center align-items-center">
                    <div className="col-6">
                        <div className="navbar-brand" role="button" onClick={() => goTo('/')}>HistoryLine</div>
                    </div>
                    <div className="col-6">


                        {/* Menu */}
                        <div className="d-flex justify-content-end gap-3">
                            <button className="navbar-toggler d-md-none" type="button" data-bs-toggle="offcanvas" data-bs-target="#mobileMenu"
                                    aria-controls="mobileMenu" aria-label="Toggle navigation">
                                <span className="navbar-toggler-icon"></span>
                            </button>

                            <div className="offcanvas offcanvas-top text-bg-dark d-md-none" tabIndex="-1" id="mobileMenu"
                                 aria-labelledby="mobileMenuLabel">
                                <div className="offcanvas-header">
                                    <h5 className="offcanvas-title" id="mobileMenuLabel">Menu</h5>
                                    <button type="button" className="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                                </div>
                                <div className="offcanvas-body d-flex flex-column gap-3 fs-5">
                                    {language === 'italian' ? (
                                        <>
                                            <div role="button" onClick={() => goTo('/')}>Home</div>
                                            <div role="button" onClick={() => goTo('/about')}>Chi siamo</div>
                                        </>
                                    ) : (
                                        <>
                                            <div role="button" onClick={() => goTo('/')}>Home</div>
                                            <div role="button" onClick={() => goTo('/about')}>About</div>
                                        </>
                                    )}
                                </div>
                            </div>


                            <div className="d-none d-md-flex flex-row ">
                                {language === 'italian' &&
                                    <>
                                        <div className="m-2" onClick={() => goTo('/')} role="button">Home</div>
                                        <div className="m-2" onClick={() => goTo('/about')} role="button">Chi siamo</div>
                                    </>
                                }
                                {language === 'english' &&
                                    <>
                                        <div className="m-2"  onClick={() => goTo('/')} role="button">Home</div>
                                        <div className="m-2"  onClick={() => goTo('/about')} role="button">About</div>
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