type ClientCardProps = {
    username: string,
    disconnect: () => void
}

const ClientCard = ({ username, disconnect }: ClientCardProps) => {
    return (
        <div>
            <p>Bonjour <strong>{username}</strong></p>
            <button className='btn btn-danger' onClick={() => disconnect()}>Se déconnecter <i className="bi bi-box-arrow-right"></i></button>
        </div>
    )
}

export default ClientCard