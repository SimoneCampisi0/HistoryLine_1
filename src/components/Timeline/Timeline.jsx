import './Timeline.css';
import dayjs from "dayjs";
import 'dayjs/locale/en.js'
import PropTypes from "prop-types";
dayjs.locale('en');

function Timeline({ resultEvents }) {
    //TODO: aggiungere supporto date AC e DC
    return (
        <>
            <section className="py-5">
                <ul className="timeline">
                    {resultEvents.events.map((item, index) => {
                        const date = dayjs(item.eventDate);
                        const formattedDate = `${date.date()} ${date.format('MMMM')} ${date.year()}`;
                        return (
                            <li className="timeline-item mb-5" key={index}>
                                <h5 className="fw-bold text-white">{item.eventName}</h5>
                                <p className="text-muted mb-2 fw-bold text-white">{formattedDate}</p>
                                <p className="text-muted">
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
    resultEvents: PropTypes.shape({
        characterName: PropTypes.string,
        link: PropTypes.string,
        events: PropTypes.array.isRequired,
    }).isRequired,
}
export default Timeline;