/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.UNal.ArquitecturaDeSoftware.Bienestar.AccesoDatos.DAO;

import co.edu.UNal.ArquitecturaDeSoftware.Bienestar.AccesoDatos.Entity.UsuarioEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author snipercat
 */
public class UsuarioDAO extends CrudDAO<UsuarioEntity> {

    public static int verificarUsuarioConvocatoriaViajes(String correo) {
        	ArrayList<Object> param = new ArrayList<>();
		param.add(correo);

		int count;
		ResultSet rs = CrudDAO.query("SELECT COUNT(*) c\n" +
                                            "FROM USUARIO US JOIN USUARIO_CONVOCATORIA REL\n" +
                                            "        ON(US.ID_USUARIO = REL.ID_USUARIO) JOIN CONVOCATORIA CON\n" +
                                            "        ON(CON.ID_CONVOCATORIA = REL.ID_CONVOCATORIA)\n" +
                                            "WHERE   UPPER(CON.NOMBRE) LIKE '%VIAJE%'\n" +
                                            "        AND US.EMAIL = ?", param);

		try {
			rs.first();
			count = rs.getInt("c");
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.verificarUsuarioConvocatoriaViajes: " + e.getMessage());
			return 0;
		}
		return count;
    }

	public UsuarioDAO() {
	}

    //this.create(1234, "CC", "probar", "crear", "crear@unal.edu.co", "clave1", 'A');
	//this.update(12, 1234, "CC", "GATO", "FELIX", "crear@unal.edu.co", "clave1", 'A');
	//this.delete(12);
	/**
	 * Returns a value object that corresponds to the user whose username and
	 * password are like the specified ones
	 *
	 * @param username String containing the username
	 * @return Value object with required user information
	 */
	public static UsuarioEntity getByUsername(String username) {
		ResultSet rs = CrudDAO.query("SELECT * FROM USUARIO WHERE LOWER(EMAIL) =LOWER(?)", new String[]{username});
		try {

			rs.first();
			UsuarioEntity ue = toEntity(rs);
			return ue;
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.getByUsername: " + e.getMessage());
			return new UsuarioEntity();
		}
	}

	//Revisar    
	public static UsuarioEntity getById(int ID_USUARIO) {
		ResultSet rs = CrudDAO.query("SELECT * FROM USUARIO WHERE ID_USUARIO = ?;", new String[]{Integer.toString(ID_USUARIO)});
		try {
			rs.first();
			UsuarioEntity ue = toEntity(rs);

			return ue;
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.getById: " + e.getMessage());
			return new UsuarioEntity();
		}
	}

	/**
	 * Busca un usuario por el documento.
	 *
	 * @param DOCUMENTO
	 * @return
	 */
	public static UsuarioEntity getByDocumento(String DOCUMENTO) {
		ResultSet rs = CrudDAO.query("SELECT * FROM USUARIO WHERE DOCUMENTO = ?;", new String[]{DOCUMENTO});
		try {
			rs.first();
			UsuarioEntity ue = toEntity(rs);

			return ue;
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.getByDocumento: " + e.getMessage());
			return new UsuarioEntity();
		}
	}

        /**
	 * Crea un usuario
	 *
	 * @param DOCUMENTO
	 * @param T_DOCUMENTO
	 * @param NOMBRES
	 * @param APELLIDOS
	 * @param EMAIL
	 * @param PASSWORD
	 * @param ROL
	 * @return OK SI EL REGISTRO EN LA BD ES CORRECTO, SI NO DEVOLVER EL TIPO DE
	 * ERROR.
	 */
	public static String create(
			String DOCUMENTO,
			String T_DOCUMENTO,
			String NOMBRES,
			String APELLIDOS,
			String EMAIL,
			String PASSWORD,
			char ROL
	){
            return create(DOCUMENTO, T_DOCUMENTO, NOMBRES, APELLIDOS, EMAIL, PASSWORD, ROL, null);
        }
        
        
	/**
	 * Crea un usuario
	 *
	 * @param DOCUMENTO
	 * @param T_DOCUMENTO
	 * @param NOMBRES
	 * @param APELLIDOS
	 * @param EMAIL
	 * @param PASSWORD
	 * @param ROL
         * @param ExtraData
	 * @return OK SI EL REGISTRO EN LA BD ES CORRECTO, SI NO DEVOLVER EL TIPO DE
	 * ERROR.
	 */
	public static String create(
			String DOCUMENTO,
			String T_DOCUMENTO,
			String NOMBRES,
			String APELLIDOS,
			String EMAIL,
			String PASSWORD,
			char ROL,
                        String ExtraData
	) {
		String respuestaSQL = update(
				"insert into USUARIO values(null,?,?,?,?,?,?,?,?)",
				new String[]{
					DOCUMENTO,
					T_DOCUMENTO,
					NOMBRES,
					APELLIDOS,
					EMAIL,
					PASSWORD,
					ROL + "",
                                        ExtraData
				}
		);
		if (respuestaSQL.contains("Duplicate entry")) {
			if (respuestaSQL.contains("DOCUMENTO")) {
				respuestaSQL = "documento";
			} else if (respuestaSQL.contains("EMAIL")) {
				respuestaSQL = "correo";
			}
		}
		System.out.println("UsuarioDAO.create: " + respuestaSQL);
		return respuestaSQL;
	}

        
        /**
	 * Actualiza un usuario
	 *
	 * @param ID_USUARIO
	 * @param DOCUMENTO
	 * @param T_DOCUMENTO
	 * @param NOMBRES
	 * @param APELLIDOS
	 * @param EMAIL
	 * @param PASSWORD
	 * @param ROL
	 * @return OK SI EL REGISTRO EN LA BD ES CORRECTO, SI NO DEVOLVER EL TIPO DE
	 * ERROR.
	 */
	public static String update(
			int ID_USUARIO,
			String DOCUMENTO,
			String T_DOCUMENTO,
			String NOMBRES,
			String APELLIDOS,
			String EMAIL,
			String PASSWORD,
			char ROL
	){
            return update(ID_USUARIO, DOCUMENTO, T_DOCUMENTO, NOMBRES, APELLIDOS, EMAIL, PASSWORD, ROL, null);
        }
        
        
	/**
	 * Actualiza un usuario
	 *
	 * @param ID_USUARIO
	 * @param DOCUMENTO
	 * @param T_DOCUMENTO
	 * @param NOMBRES
	 * @param APELLIDOS
	 * @param EMAIL
	 * @param PASSWORD
	 * @param ROL
         * @param ExtraData
	 * @return OK SI EL REGISTRO EN LA BD ES CORRECTO, SI NO DEVOLVER EL TIPO DE
	 * ERROR.
	 */
	public static String update(
			int ID_USUARIO,
			String DOCUMENTO,
			String T_DOCUMENTO,
			String NOMBRES,
			String APELLIDOS,
			String EMAIL,
			String PASSWORD,
			char ROL,
                        String ExtraData
	) {
		String respuestaSQL = CrudDAO.update(
				"UPDATE USUARIO\n"
				+ "SET DOCUMENTO = ?,\n"
				+ "T_DOCUMENTO = ?,\n"
				+ "NOMBRES = ?,\n"
				+ "APELLIDOS = ?,\n"
				+ "EMAIL = ?,\n"
				+ "PASSWORD = ?,\n"
				+ "ROL = ?\n"
                                + "DATOS_EXTRA = ?\n"
				+ "WHERE ID_USUARIO = ?;",
				new String[]{
					DOCUMENTO,
					T_DOCUMENTO,
					NOMBRES,
					APELLIDOS,
					EMAIL,
					PASSWORD,
					ROL + "",
                                        ExtraData,
					Integer.toString(ID_USUARIO)
				}
		);
		if (respuestaSQL.contains("Duplicate entry")) {
			if (respuestaSQL.contains("DOCUMENTO")) {
				respuestaSQL = "documento";
			} else if (respuestaSQL.contains("EMAIL")) {
				respuestaSQL = "correo";
			}
		}
		System.out.println("UsuarioDAO.update: " + respuestaSQL);
		return respuestaSQL;
	}

	/**
	 *
	 * @param ID_USUARIO
	 * @return
	 */
	public static String delete(int ID_USUARIO) {
		String respuestaSQL = CrudDAO.update(
				"DELETE FROM USUARIO WHERE ID_USUARIO = ?;",
				new String[]{Integer.toString(ID_USUARIO)}
		);
		System.out.println("UsuarioDAO.delete: " + respuestaSQL);
		return respuestaSQL;
	}

	/**
	 * Ingresa un registro en la tabla USUARIO_TALLER
	 *
	 * @param ID_USUARIO
	 * @param ID_TALLER
	 * @return
	 */
	public static String registrarTaller(int ID_USUARIO, int ID_TALLER) {
		String respuestaSQL = CrudDAO.update(
				"INSERT INTO USUARIO_TALLER(ID_USUARIO, ID_TALLER) VALUES (?,?);",
				new String[]{Integer.toString(ID_USUARIO), Integer.toString(ID_TALLER)}
		);
		System.out.println("UsuarioDAO.registrarTaller: " + respuestaSQL);
		return respuestaSQL;
	}

	/**
	 * Ingresa un registro en la tabla USUARIO_TALLER entregandole el documento
	 * del usuario y el id de taller
	 *
	 * @param DOCUMENTO
	 * @param ID_TALLER
	 * @return
	 */
	public static String registrarTallerByDocumento(String DOCUMENTO, int ID_TALLER) {

		UsuarioEntity us = getByDocumento(DOCUMENTO);
		return registrarTaller(us.getIdUsuario(), ID_TALLER);
	}

	/**
	 * Ingresa un registro en la tabla USUARIO_CONVOCATORIA entregandole el
	 * documento del usuario y el id de taller
	 *
	 * @param DOCUMENTO
	 * @param ID_TALLER
	 * @return
	 */
	public static String registrarConvocatoriaByDocumento(String DOCUMENTO, int ID_TALLER) {

		UsuarioEntity us = getByDocumento(DOCUMENTO);
		return registrarTaller(us.getIdUsuario(), ID_TALLER);
	}

	/**
	 * Ingresa un registro en la tabla USUARIO_CONVOCATORIA
	 *
	 * @param ID_USUARIO
	 * @param ID_CONVOCATORIA
	 * @return
	 */
	public static String registrarConvocatoria(int ID_USUARIO, int ID_CONVOCATORIA) {
		String respuestaSQL = CrudDAO.update(
				"INSERT INTO USUARIO_CONVOCATORIA(ID_USUARIO, ID_CONVOCATORIA) VALUES (?,?);",
				new String[]{Integer.toString(ID_USUARIO), Integer.toString(ID_CONVOCATORIA)}
		);
		System.out.println("UsuarioDAO.registrarConvocatoria: " + respuestaSQL);
		return respuestaSQL;
	}

	/**
	 * Elimina un registro en la tabla USUARIO_CONVOCATORIA
	 *
	 * @param ID_USUARIO
	 * @param ID_CONVOCATORIA
	 * @return
	 */
	public static String desvincularConvocatoria(int ID_USUARIO, int ID_CONVOCATORIA) {

		String respuestaSQL = CrudDAO.update(
				"DELETE FROM USUARIO_CONVOCATORIA WHERE ID_USUARIO = ? AND ID_CONVOCATORIA = ?;",
				new String[]{Integer.toString(ID_USUARIO), Integer.toString(ID_CONVOCATORIA)}
		);
		System.out.println("UsuarioDAO.desvincularConvocatoria: " + respuestaSQL);
		return respuestaSQL;
	}

	/**
	 * Elimina un registro en la tabla USUARIO_TALLER
	 *
	 * @param ID_USUARIO
	 * @param ID_TALLER
	 * @return
	 */
	public static String desvincularTaller(int ID_USUARIO, int ID_TALLER) {

		String respuestaSQL = CrudDAO.update(
				"DELETE FROM USUARIO_TALLER WHERE ID_USUARIO = ? AND ID_TALLER = ?;",
				new String[]{Integer.toString(ID_USUARIO), Integer.toString(ID_TALLER)}
		);
		System.out.println("UsuarioDAO.desvincularTaller: " + respuestaSQL);
		return respuestaSQL;
	}

	/**
	 * Retorna la lista de usuarios en un rango
	 *
	 * @param tamano Cantidad de registros a devolver
	 * @param pagina pagina en la que se se está
	 * @return
	 */
	public static ArrayList<UsuarioEntity> getUsuarios(int tamano, int pagina) {
		int posicion = pagina * tamano;

		ArrayList<Object> param = new ArrayList<>();
		param.add(posicion);
		param.add(tamano);

		ArrayList<UsuarioEntity> usuarios = new ArrayList<>();
		ResultSet rs = CrudDAO.query("SELECT * FROM USUARIO LIMIT ?,?", param);
		try {
			while (rs.next()) {
				UsuarioEntity ue = toEntity(rs);
				usuarios.add(ue);
			}
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.getUsuarios: " + e.getMessage());
			return new ArrayList<>();
		}
		return usuarios;
	}

	/**
	 * Retorna la cantidad de registros en la tabla USUARIOS
	 *
	 * @return
	 */
	public static int getCountUsuarios() {
		ArrayList<Object> param = new ArrayList<>();

		int count;
		ResultSet rs = CrudDAO.query("SELECT count(*) c FROM USUARIO", param);
		try {
			rs.first();
			count = rs.getInt("c");
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.getCountUsuarios: " + e.getMessage());
			return 0;
		}
		return count;
	}

	/**
	 * Retorna la cantidad de Talleres
	 *
	 * @return
	 */
	public static int getCountTalleres() {
		ArrayList<Object> param = new ArrayList<>();

		int count;
		ResultSet rs = CrudDAO.query("SELECT COUNT(*) c FROM TALLER WHERE TIPO_TALLER = 'T'", param);

		try {
			rs.first();
			count = rs.getInt("c");
		} catch (SQLException e) {
			System.out.println("TallerDAO.getCountTodosTaller: " + e.getMessage());
			return 0;
		}
		return count;
	}

	/**
	 * Retorna la cantidad de USUARIOS inscritos en un Taller
	 *
	 * @param ID_TALLER
	 * @return
	 */
	public static int getCountUsuarioTalleres(int ID_TALLER) {
		ArrayList<Object> param = new ArrayList<>();
		param.add(ID_TALLER);

		int count;
		ResultSet rs = CrudDAO.query("SELECT COUNT(*) c FROM USUARIO_TALLER WHERE ID_TALLER = ?", param);

		try {
			rs.first();
			count = rs.getInt("c");
		} catch (SQLException e) {
			System.out.println("TallerDAO.getCountUsuarioTalleres: " + e.getMessage());
			return 0;
		}
		return count;
	}

	/**
	 * Retorna la lista de usuarios asociados a un taller
	 *
	 * @param ID_TALLER
	 * @param tamano Cantidad de registros a devolver
	 * @param pagina pagina en la que se se está
	 * @return
	 */
	public static ArrayList<UsuarioEntity> getUsuariosByTaller(int ID_TALLER, int tamano, int pagina) {
		int posicion = pagina * tamano;

		ArrayList<Object> param = new ArrayList<>();
		param.add(ID_TALLER);
		param.add(posicion);
		param.add(tamano);

		ArrayList<UsuarioEntity> usuarios = new ArrayList<>();
		ResultSet rs = CrudDAO.query("select USUARIO.*\n"
			+ "FROM USUARIO_TALLER JOIN USUARIO ON (USUARIO_TALLER.ID_USUARIO = USUARIO.ID_USUARIO)\n"
			+ "WHERE ID_TALLER = ?\n"
			+ "LIMIT ?,?", param);
		try {
			while (rs.next()) {
				UsuarioEntity ue = toEntity(rs);
				usuarios.add(ue);
			}
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.getUsuariosByTaller: " + e.getMessage());
			return new ArrayList<>();
		}
		return usuarios;
	}

	/**
	 * Retorna la lista de profesores asociados a una convocatoria
	 *
	 * @param ID_TALLER
	 * @param tamano Cantidad de registros a devolver
	 * @param pagina pagina en la que se se está
	 * @return
	 */
	public static ArrayList<UsuarioEntity> getProfesoresByTaller(int ID_TALLER, int tamano, int pagina) {
		int posicion = pagina * tamano;

		ArrayList<Object> param = new ArrayList<>();
		param.add(ID_TALLER);
		param.add(posicion);
		param.add(tamano);

		ArrayList<UsuarioEntity> usuarios = new ArrayList<>();
		ResultSet rs = CrudDAO.query("select USUARIO.*\n"
			+ "FROM PROFESOR_TALLER JOIN USUARIO ON (PROFESOR_TALLER.ID_PROFESOR = USUARIO.ID_USUARIO)\n"
			+ "WHERE ID_TALLER = ?\n"
			+ "LIMIT ?,?", param);
		try {
			while (rs.next()) {
				UsuarioEntity ue = toEntity(rs);
				usuarios.add(ue);
			}
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.getUsuariosByTaller: " + e.getMessage());
			return new ArrayList<>();
		}
		return usuarios;
	}

	/**
	 * Retorna la lista de usuarios asociados a una convocatoria
	 *
	 * @param ID_CONVOCATORIA
	 * @param tamano Cantidad de registros a devolver
	 * @param pagina pagina en la que se se está
	 * @return
	 */
	public static ArrayList<UsuarioEntity> getUsuariosByConvocatoria(int ID_CONVOCATORIA, int tamano, int pagina) {
		int posicion = pagina * tamano;

		ArrayList<Object> param = new ArrayList<>();
		param.add(ID_CONVOCATORIA);
		param.add(posicion);
		param.add(tamano);

		ArrayList<UsuarioEntity> usuarios = new ArrayList<>();
		ResultSet rs = CrudDAO.query("select USUARIO.*\n"
			+ "FROM USUARIO_CONVOCATORIA JOIN USUARIO ON (USUARIO_CONVOCATORIA.ID_USUARIO = USUARIO.ID_USUARIO)\n"
			+ "WHERE ID_CONVOCATORIA = ?\n"
			+ "LIMIT ?,?", param);
		try {
			while (rs.next()) {
				UsuarioEntity ue = toEntity(rs);
				usuarios.add(ue);
			}
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.getUsuariosByConvocatoria: " + e.getMessage());
			return new ArrayList<>();
		}
		return usuarios;
	}
        
        
        /**
         * Determina si un usuario está asociado o no a un taller, ya sea como profesor o como usuario
         * @param ID_USUARIO
         * @param ID_TALLER
         * @return 
         */
        public static boolean asociadoConTaller( int ID_USUARIO, int ID_TALLER ){
            

		ArrayList<Object> param = new ArrayList<>();
		param.add(ID_USUARIO);
		param.add(ID_TALLER);
                int count;
                ResultSet rs = CrudDAO.query("select count(*) 'c' from USUARIO_PROFESOR_TALLER\n" +
                                             "where ID_USUARIO = ? and ID_TALLER = ? ", param);
                try {
			rs.first();
			count = rs.getInt("c");
                        if(count == 0)
                            return false;
                        else
                            return true;
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.asociadoConTaller: " + e.getMessage());
			return false;
		}
        }
        
        
        /**
         * Determina si un usuario está asociado o no a una convocatoria
         * @param ID_USUARIO
         * @param ID_CONVOCATORIA
         * @return 
         */
        public static boolean asociadoConConvocatoria( int ID_USUARIO, int ID_CONVOCATORIA ){
            

		ArrayList<Object> param = new ArrayList<>();
		param.add(ID_USUARIO);
		param.add(ID_CONVOCATORIA);
                int count;
                ResultSet rs = CrudDAO.query("select count(*) 'c' from USUARIO_CONVOCATORIA\n" +
                                             "where ID_USUARIO = ? and ID_CONVOCATORIA = ? ", param);
                try {
			rs.first();
			count = rs.getInt("c");
                        if(count == 0)
                            return false;
                        else
                            return true;
		} catch (SQLException e) {
			System.out.println("UsuarioDAO.asociadoConConvocatoria: " + e.getMessage());
			return false;
		}
        }
        

	@Override
	protected Class getEntityClass() {
		return UsuarioEntity.class;
	}

	/**
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	protected static UsuarioEntity toEntity(ResultSet rs) throws SQLException {
		return new UsuarioEntity(
			rs.getInt("ID_USUARIO"),
			rs.getString("DOCUMENTO"),
			rs.getString("T_DOCUMENTO"),
			rs.getString("NOMBRES"),
			rs.getString("APELLIDOS"),
			rs.getString("EMAIL"),
			rs.getString("PASSWORD"),
			rs.getString("ROL").charAt(0),
                        rs.getString("DATOS_EXTRA")
		);
	}
}