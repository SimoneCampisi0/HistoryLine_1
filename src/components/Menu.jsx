import {GB, IT, US} from "country-flag-icons/react/1x1";
import './Menu.css'
import Select from 'react-select'
function Menu({ language }) {

    //TODO: fixare CSS Select
    const options = [
        { value: 'gb', label: (<><GB style={{marginRight:4}}/> </>) },
        { value: 'it', label: (<><IT style={{marginRight:4}}/> </>) },
    ];


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

                    <Select options={options} />;

                </div>
            </div>

        </>
    )
}

export default Menu;