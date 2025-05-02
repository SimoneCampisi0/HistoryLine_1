import './Timeline.css';
import dayjs from "dayjs";
import 'dayjs/locale/it.js'
import PropTypes from "prop-types";
dayjs.locale('it');

function Timeline({ resultEvents }) {
    return (
        <>
            <section className="py-5">
                <ul className="timeline">
                    {resultEvents.events.map((item, index) => {
                        const formattedDate = dayjs(item.eventDate).format('D MMMM YYYY');
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