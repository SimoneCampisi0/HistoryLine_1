import "./Description.css";
function Description({ characterSelected }) {


    return (
        <>
            <div className="text-white row border border-white rounded-3 p-4">
                <div className="col-md-12 col-lg-3 mb-3 mb-md-4 d-flex justify-content-center align-items-center">
                    <img className="character-picture rounded-3 " src={characterSelected.imageUrl} alt="Character image"/>
                </div>
                <div className="col-md-12 col-lg-9 d-flex flex-column justify-content-center align-items-center">
                    <div className="h3">{characterSelected.characterDescription.characterName}</div>
                    <div>{characterSelected.characterDescription.description}</div>
                </div>
            </div>
        </>
    )
}
export default Description;