package ro.mpp.teledon.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp.teledon.JdbcUtils;
import ro.mpp.teledon.model.CazCaritabil;
import ro.mpp.teledon.model.Donatie;
import ro.mpp.teledon.model.Donator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class DonatieDBRepository implements IDonatieRepository {
    private JdbcUtils dbUtils;
    protected DonatorDBRepository donatorDBRepository;
    protected CazCaritabilDBRepository cazCaritabilDBRepository;

    private static final Logger logger= (Logger) LogManager.getLogger();

    public DonatieDBRepository(Properties properties, DonatorDBRepository donatorDBRepository, CazCaritabilDBRepository cazCaritabilDBRepository) {
        logger.info("Initializing DonatieDBRepository with properties: {} ",properties);
        dbUtils = new JdbcUtils(properties);
        this.donatorDBRepository = donatorDBRepository;
        this.cazCaritabilDBRepository = cazCaritabilDBRepository;
    }

    @Override
    public void add(Donatie elem) {
        logger.traceEntry("Saving task {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("insert into donatie (id_donator, id_caz_caritabil, suma_donata) values (?, ?, ?)"))
        {
            preStmt.setInt(1, elem.getDonator().getId());
            preStmt.setInt(2, elem.getCazCaritabil().getId());
            preStmt.setInt(3, elem.getSumaDonata());
            String updateCazCaritabilSql = "UPDATE caz_caritabil SET suma_stransa = suma_stransa + ? WHERE id = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(updateCazCaritabilSql)) {
                updateStmt.setInt(1, elem.getSumaDonata());
                updateStmt.setInt(2, elem.getCazCaritabil().getId());
                updateStmt.executeUpdate();
            }


            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Donatie elem) {
        logger.traceEntry("Deleting task {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("delete from donatie where id =?"))
        {
            preStmt.setInt(1, elem.getId());
            int result=preStmt.executeUpdate();
            logger.trace("Deleted {} instances", result);
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Donatie elem, Integer id) {
        logger.traceEntry("Updating DONATIE with ID: {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("UPDATE donatie SET id_donator = ?, id_caz_caritabil = ?, suma_donata = ? WHERE id = ?")) {
            preStmt.setInt(1, elem.getDonator().getId());
            preStmt.setInt(2, elem.getCazCaritabil().getId());
            preStmt.setInt(3, elem.getSumaDonata());
            preStmt.setInt(4, id);
            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public Donatie findById(Integer id) {
        logger.traceEntry("Find by ID: {}", id);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("select * from donatie where id=?"))
        {
            preStmt.setInt(1, id);
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    Integer idDonator=result.getInt("id_donator");
                    Integer idCazCaritabil=result.getInt("id_caz_caritabil");
                    Integer sumaDonata=result.getInt("suma_donata");
                    Donator donator=donatorDBRepository.findById(idDonator);
                    CazCaritabil cazCaritabil=cazCaritabilDBRepository.findById(idCazCaritabil);
                    Donatie donatie=new Donatie(donator, cazCaritabil, sumaDonata);
                    donatie.setId(id);
                    return donatie;
                }
            }
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<Donatie> findAll() {
        logger.traceEntry("Find all {}");
        Connection con=dbUtils.getConnection();
        List<Donatie> listaDonatii=new ArrayList<>();
        try(PreparedStatement preStmt= con.prepareStatement("select * from donatie"))
        {
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    Integer id=result.getInt("id");
                    Integer idDonator=result.getInt("id_donator");
                    Integer idCazCaritabil=result.getInt("id_caz_caritabil");
                    Integer sumaDonata=result.getInt("suma_donata");
                    Donator donator=donatorDBRepository.findById(idDonator);
                    CazCaritabil cazCaritabil=cazCaritabilDBRepository.findById(idCazCaritabil);
                    Donatie donatie=new Donatie(donator, cazCaritabil, sumaDonata);
                    donatie.setId(id);
                    listaDonatii.add(donatie);
                }
            }
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }logger.traceExit(listaDonatii);
        return listaDonatii;
    }

    @Override
    public Collection<Donatie> getAll() {
        logger.traceEntry("Get all {}");
        Connection con=dbUtils.getConnection();
        List<Donatie> listaDonatii=new ArrayList<>();
        try(PreparedStatement preStmt= con.prepareStatement("select * from donatie"))
        {
            try(ResultSet result=preStmt.executeQuery())
            {
                while (result.next())
                {
                    Integer id=result.getInt("id");
                    Integer idDonator=result.getInt("id_donator");
                    Integer idCazCaritabil=result.getInt("id_caz_caritabil");
                    Integer sumaDonata=result.getInt("suma_donata");
                    Donator donator=donatorDBRepository.findById(idDonator);
                    CazCaritabil cazCaritabil=cazCaritabilDBRepository.findById(idCazCaritabil);
                    Donatie donatie=new Donatie(donator, cazCaritabil, sumaDonata);
                    donatie.setId(id);
                    listaDonatii.add(donatie);
                }
            }
        }catch(SQLException ex)
        {
            logger.error(ex);
            System.err.println("Error DB "+ ex);
        }logger.traceExit(listaDonatii);
        return listaDonatii;
    }
    @Override
    public Integer findIdByDonatorCazCaritabilSuma(Integer donatorId, Integer cazCaritabilId, Integer sumaDonata) {
        logger.traceEntry("Find ID by donor ID: {}, charitable case ID: {}, and donated amount: {}", donatorId, cazCaritabilId, sumaDonata);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT id FROM donatie WHERE id_donator = ? AND id_caz_caritabil = ? AND suma_donata = ?")) {
            preStmt.setInt(1, donatorId);
            preStmt.setInt(2, cazCaritabilId);
            preStmt.setInt(3, sumaDonata);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Integer id = result.getInt("id");
                    logger.traceExit("Found ID: {}", id);
                    return id;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit("No ID found");
        return null;
    }
}
