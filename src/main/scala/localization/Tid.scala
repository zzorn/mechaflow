package localization

/**
 * A translation ID, used to retrieve user readable name, description, icon, etc for
 * some object or concept.
 *
 * path can be a dot separated name path (to allow grouping related things together).
 */
case class Tid(path: String)