const Button = props => {
    return (
        <button
            type={props.type}
            className={props.className}
            onClick={props.handleClick}
        >
            {props.label}
        </button>
      );
}
 
export default Button;