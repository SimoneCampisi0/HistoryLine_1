import {useState} from "react";
import {getSuggestCharacters} from "../services/CharacterService.js";

function Search() {
    const [searchText, setSearchText] = useState('');
    const [errorMessage, setErrorMessage] = useState(null);

    async function getSuggestCharactersList() {
        const object = {
            name: searchText,
        }
        const response = await getSuggestCharacters(object);
        console.log("list suggest: ", response);
    }

    function onSearchButton () {
        console.log("searchText: ", searchText);
        if (searchText.trim() === "") {
            setErrorMessage({
                errorName: "Search empty.",
                message: "Insert a text to search.",
                code: "001",
            })
            return;
        }

        setErrorMessage(null);
        getSuggestCharactersList();
    }

    function onChangeText (value) {
        setSearchText(value)
    }

    return (
        <>
            <div className="form-group d-flex flex-column justify-content-center align-items-center p-4 gap-4 text-white">
                <div className="h2">Discovery life of a historical character!</div>
                {/*Modificare width con la lunghezza del div sopra*/}
                <input
                    style={{ width: 552 }}
                    className="form-control"
                    type="text"
                    placeholder="Search historical character..."
                    value={searchText}
                    onChange={e => onChangeText(e.target.value)}
                />
                {errorMessage && (
                    <div>
                        {errorMessage.message}
                    </div>
                )}
                <button className="btn btn-dark" onClick={onSearchButton}>Search</button>
            </div>
        </>
    )
}

export default Search;