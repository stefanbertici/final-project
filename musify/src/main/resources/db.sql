CREATE TABLE users (
	id INT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(45),
	last_name VARCHAR(45),
	email VARCHAR(45),
	encrypted_password VARCHAR(45),
	country VARCHAR(45),
	role VARCHAR(45),
	status VARCHAR(45),
	PRIMARY KEY (id)
  );
  
  CREATE TABLE playlists (
	id INT NOT NULL AUTO_INCREMENT,
	owner_user_id INT,
	type VARCHAR(45),
	created_date DATE,
	updated_date DATE,
	PRIMARY KEY (id),
    FOREIGN KEY (owner_user_id)
		REFERENCES users(id)
  );
  
  CREATE TABLE songs (
	id INT NOT NULL AUTO_INCREMENT,
	title VARCHAR(45),
	duration TIME,
	created_date DATE,
	PRIMARY KEY (id)
  );
  
  CREATE TABLE alternative_song_titles (
	id INT NOT NULL AUTO_INCREMENT,
	song_id INT,
	alternative_title VARCHAR(45),
	PRIMARY KEY (id),
    FOREIGN KEY (song_id)
		REFERENCES songs(id)
  );
  
  CREATE TABLE albums (
	id INT NOT NULL AUTO_INCREMENT,
    artist_id INT,
    band_id INT,
	title VARCHAR(45),
	description VARCHAR(45),
    genre VARCHAR(45),
	release_date DATE,
    label VARCHAR(45),
	PRIMARY KEY (id),
    FOREIGN KEY (artist_id)
		REFERENCES artists(id),
	FOREIGN KEY (band_id)
		REFERENCES bands(id)
  );
  
  CREATE TABLE artists (
	id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    stage_name VARCHAR(45),
    birthday DATE,
	activity_start_date VARCHAR(45),
	activity_end_date VARCHAR(45),
	PRIMARY KEY (id)
  );
  
  CREATE TABLE bands (
	id INT NOT NULL AUTO_INCREMENT,
	band_name VARCHAR(45),
    location VARCHAR(45),
    activity_start_date VARCHAR(45),
	activity_end_date VARCHAR(45),
	PRIMARY KEY (id)
  );
  
   CREATE TABLE playlists_songs (
	id INT NOT NULL AUTO_INCREMENT,
	playlist_id INT,
	song_id INT,
	PRIMARY KEY (id),
    FOREIGN KEY (playlist_id)
		REFERENCES playlists(id),
	FOREIGN KEY (song_id)
		REFERENCES songs(id)
  );
  
  CREATE TABLE albums_songs (
	id INT NOT NULL AUTO_INCREMENT,
	album_id INT,
	song_id INT,
	PRIMARY KEY (id),
    FOREIGN KEY (album_id)
		REFERENCES albums(id),
	FOREIGN KEY (song_id)
		REFERENCES songs(id)
  );
  
/*
  CREATE TABLE artists_albums (
	id INT NOT NULL AUTO_INCREMENT,
	artist_id INT,
    band_id INT,
    album_id INT,
	PRIMARY KEY (id),
	FOREIGN KEY (artist_id)
		REFERENCES artists(id),
	FOREIGN KEY (band_id)
		REFERENCES bands(id),
	FOREIGN KEY (album_id)
		REFERENCES albums(id)
  );
  */
  
  CREATE TABLE artists_songs (
	id INT NOT NULL AUTO_INCREMENT,
	artist_id INT,
	song_id INT,
	PRIMARY KEY (id),
    FOREIGN KEY (artist_id)
		REFERENCES artists(id),
	FOREIGN KEY (song_id)
		REFERENCES songs(id)
  );
  
  CREATE TABLE bands_artists (
	id INT NOT NULL AUTO_INCREMENT,
	band_id INT,
	artist_id INT,
	PRIMARY KEY (id),
    FOREIGN KEY (band_id)
		REFERENCES bands(id),
	FOREIGN KEY (artist_id)
		REFERENCES artists(id)
  );
  
  CREATE TABLE users_playlists (
	id INT NOT NULL AUTO_INCREMENT,
	user_id INT,
	playlist_id INT,
	PRIMARY KEY (id),
    FOREIGN KEY (user_id)
		REFERENCES users(id),
	FOREIGN KEY (playlist_id)
		REFERENCES playlists(id)
  );
  
  