import {useEffect, useState} from "react";
import {getCharactersEvent, getSuggestCharacters} from "../../services/CharacterService.js";
import './Search.css';
import PropTypes from "prop-types";

function Search({ language, resultEvents, setResultEvent}) {
    const [searchedParams, setSearchedParams] = useState({
        text: '',
        origin: '',
        alreadySearched: false,
    });
    const [errorMessage, setErrorMessage] = useState(null);
    const [suggestResults, setSuggestResults] = useState([]);
    const [showSuggest, setShowSuggest] = useState(false);
    const [selected, setSelected] = useState(null);
    const [loading, setLoading] = useState(false);

    async function getSuggestCharactersList() {
        const req = {
            name: searchedParams.text,
            languageName: language
        }
        return await getSuggestCharacters(req);
    }

    async function getCharactersEventList(request) {
        return await getCharactersEvent(request);
    }

    function clearSearch() {
        setSearchedParams({
            text: '',
            origin: '',
            alreadySearched: true
        });
        setSuggestResults(null);
        setResultEvent(null);
        console.log("clearSearch");
    }

    function validateText() {
        if (searchedParams.text.trim() === "") {
            if(language === 'italian') {
                setErrorMessage({
                    errorName: "Search empty.",
                    message: "Inserisci un testo per la ricerca.",
                    code: "001",
                });
            } else if(language === 'english') {
                setErrorMessage({
                    errorName: "Search empty.",
                    message: "Insert a text to search.",
                    code: "001",
                });
            }
            setResultEvent(null);
            return false;
        }
        return true;
    }

    function onSelectedValue (item) {
        console.log("searched: ", item);
        setSelected(item);
        setSearchedParams({
            ...searchedParams,
            text: item.itemLabel,
            origin: 'suggest'
        });
        setShowSuggest(false);
        setErrorMessage(null);
    }

    /* TODO: aggiungere blocco di click a ripetizione */
    async function onSearchButton () {
        if(!searchedParams.alreadySearched || errorMessage) return;
        setErrorMessage(null);
        if(selected === null || selected === undefined) {
            if(suggestResults && suggestResults.length > 0){
                setSelected(suggestResults[0]);
                //invoco API per individuare il primo risultato

                return;
            } else {
                //implementare print "Nessun personaggio selezionato / personaggio non trovato"
                return;
            }
        }

        const req = {
            result: selected.itemLabel,
            link: selected.item,
            extraOption: null,
            languageName: language
        }

        setLoading(true);
        // getCharactersEventList(req)
        //     .then(response => {
        //         console.log("charactersEventList: ", response);
        //         setResultEvent({
        //             characterName: req.result,
        //             link: req.link,
        //             events: response
        //         });
        //     })
        try {
            const response = await getCharactersEventList(req);
            console.log("charactersEventList: ", response);
            setResultEvent({
                characterName: req.result,
                link: req.link,
                events: response
            });
        } catch (err) {
            console.error(err);
        } finally {
            setLoading(false);
        }
    }


    useEffect(() => {
        if(!searchedParams.alreadySearched) return;

        if(searchedParams.text === undefined) {
            console.log("searchText is undefined");
            return;
        }

        if(searchedParams && searchedParams.origin === 'suggest') {
            return;
        }

        setShowSuggest(true);
        const debounce = setTimeout(() => {
            if(!validateText()) {
                clearSearch();
                return;
            }
            getSuggestCharactersList(searchedParams.text)
                .then(r => {
                    console.log("response: ", r);
                    setSuggestResults(r.items);
                });
        }, 300);

        return () => clearTimeout(debounce); // Pulisco il timer se searchText varia prima che il timer scada
    }, [searchedParams.text]); // In questo modo viene rilevato ogni cambiamento allo state di searchText

    /* useEffect che rileva il cambio di lingua.
    *  Pulisce un eventuale ricerca di un personaggio storico */
    useEffect(() => {
        clearSearch();
    }, [language]);

    return (
        <>

            {/* OVERLAY DI LOADING */}
            {loading && (
                <div className="loading-overlay position-fixed top-0 start-0 w-100 h-100
                        d-flex justify-content-center align-items-center
                        bg-dark bg-opacity-50" style={{ zIndex: 1050 }}>
                    <div className="spinner-border text-light" role="status">
                        <span className="visually-hidden">Loading...</span>
                    </div>
                </div>
            )}

            {language === 'italian' &&
                <div className="form-group d-flex flex-column justify-content-center align-items-center p-4 text-white mt-5">
                    <div className="h2 mb-4">Scopri la vita di un personaggio storico!</div>
                    {/*Modificare width con la lunghezza del div sopra*/}
                    <input
                        style={{ width: 552 }}
                        className="form-control "
                        type="text"
                        placeholder="Cerca un personaggio storico..."
                        value={searchedParams.text}
                        onChange={e => setSearchedParams({
                            text: e.target.value,
                            origin: 'digit',
                            alreadySearched: true,
                        })}
                    />
                    {suggestResults && showSuggest && suggestResults.length > 0 &&
                        (
                            <div className="mt-1 mb-3">
                                <>
                                    {suggestResults.map((item, index) => (
                                        <div className="suggest-result" key={index} onClick={() => onSelectedValue(item)}>{item.itemLabel}</div>
                                    ))}
                                </>
                            </div>
                        )
                    }
                    {errorMessage && (
                        <div className="mt-4 mb-2">
                            {errorMessage.message}
                        </div>
                    )}
                    <button className="btn btn-dark mt-4" onClick={onSearchButton}>Cerca</button>
                </div>
            }
            {language === 'english' &&
                <div className="form-group d-flex flex-column justify-content-center align-items-center p-4 text-white mt-5">
                    <div className="h2 mb-4">Discovery life of a historical character!</div>
                    {/*Modificare width con la lunghezza del div sopra*/}
                    <input
                        style={{ width: 552 }}
                        className="form-control "
                        type="text"
                        placeholder="Search historical character..."
                        value={searchedParams.text}
                        onChange={e => setSearchedParams({
                            text: e.target.value,
                            origin: 'digit',
                            alreadySearched: true,
                        })}
                    />
                    {suggestResults && showSuggest && suggestResults.length > 0 &&
                        (
                            <div className="mt-1 mb-3">
                                <>
                                    {suggestResults.map((item, index) => (
                                        // <div className="rounded suggest-result" key={index} onClick={() => onSelectedValue(item)}>{item.itemLabel}</div>
                                        <div className="suggest-result" key={index} onClick={() => onSelectedValue(item)}>{item.itemLabel}</div>
                                    ))}
                                </>
                            </div>
                        )
                    }
                    {errorMessage && (
                        <div className="mt-4 mb-2">
                            {errorMessage.message}
                        </div>
                    )}
                    <button className="btn btn-dark mt-4" onClick={onSearchButton}>Search</button>
                </div>
            }
        </>
    )
}

Search.propTypes = {
    resultEvents: PropTypes.shape({
        characterName: PropTypes.string,
        link: PropTypes.string,
        events: PropTypes.array.isRequired,
    }),
    setResultEvent: PropTypes.func.isRequired,
}

export default Search;