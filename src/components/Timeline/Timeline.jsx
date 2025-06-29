import './Timeline.css';
import dayjs from "dayjs";
import 'dayjs/locale/en';
import 'dayjs/locale/it';
import PropTypes from "prop-types";

function Timeline({ characterSelected, language }) {
    const selectedLocale = language === 'italian' ? 'it' : 'en';

    return (
        <>
            <section className="py-5">
                <ul className="timeline">
                    {characterSelected.events.map((item, index) => {
                        const date = dayjs(item.eventDate).locale(selectedLocale);
                        const formattedDate = `${date.date()} ${date.format('MMMM')} ${date.year()}`;
                        return (
                            <li className="timeline-item mb-5" key={index}>
                                <h5 className="fw-bold text-white">{item.eventName}</h5>
                                {selectedLocale === 'it' && <p className="mb-2 fw-bold text-white">{formattedDate} {item.eventIsBeforeChrist ? "a.C." : ""}</p>}
                                {selectedLocale === 'en' && <p className="mb-2 fw-bold text-white">{formattedDate} {item.eventIsBeforeChrist ? "b.C." : ""}</p>}
                                <p className="text-white">
                                    {item.eventDescription}
                                </p>
                            </li>
                        );
                    })}
                </ul>
            </section>
        </>
    );
}

Timeline.propTypes = {
    characterSelected: PropTypes.shape({
        characterName: PropTypes.string,
        link: PropTypes.string,
        events: PropTypes.array.isRequired,
    }).isRequired,
    language: PropTypes.string.isRequired,
}
export default Timeline;