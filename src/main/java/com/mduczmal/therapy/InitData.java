package com.mduczmal.therapy;

import com.mduczmal.therapy.ad.Ad;
import com.mduczmal.therapy.ad.AdDetails;
import com.mduczmal.therapy.ad.AdFactory;
import com.mduczmal.therapy.ad.AdRepository;
import com.mduczmal.therapy.ad.comment.Comment;
import com.mduczmal.therapy.ad.comment.CommentRepository;
import com.mduczmal.therapy.user.Authority;
import com.mduczmal.therapy.user.AuthorityRepository;
import com.mduczmal.therapy.user.UserAccount;
import com.mduczmal.therapy.user.UserRepository;
import com.mduczmal.therapy.user.moderator.Moderator;
import com.mduczmal.therapy.user.moderator.ModeratorRepository;
import com.mduczmal.therapy.user.therapist.Therapist;
import com.mduczmal.therapy.user.therapist.TherapistRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {
    /*
    Single Responsibility - klasa odpowiada za wypełnienie bazy danych przykładowymi danymi
     */
    private final PasswordEncoder passwordEncoder;
    private final TherapistRepository therapistRepository;
    private final ModeratorRepository moderatorRepository;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final AdFactory adFactory;

    public InitData(AdRepository adRepository, TherapistRepository therapistRepository,
                    ModeratorRepository moderatorRepository, UserRepository userRepository,
                    AuthorityRepository authorityRepository, CommentRepository commentRepository,
                    PasswordEncoder passwordEncoder, AdFactory adFactory) {
        this.adRepository = adRepository;
        this.therapistRepository = therapistRepository;
        this.moderatorRepository = moderatorRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.adFactory = adFactory;
    }

    private void addModerator(String username, String password) {
        Moderator moderator = new Moderator();
        Authority authority = new Authority(username, "ROLE_MODERATOR");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        UserAccount userAccount = new UserAccount(moderator, username, passwordEncoder.encode(password), authorities);
        moderatorRepository.save(moderator);
        userRepository.save(userAccount);
        authorityRepository.save(authority);
    }

    private Therapist addTherapist(String username, String password) {
        Therapist therapist = new Therapist();
        List<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority(username, "ROLE_THERAPIST");
        authorities.add(authority);
        UserAccount userAccount = new UserAccount(therapist, username,
                passwordEncoder.encode(password), authorities);
        therapistRepository.save(therapist);
        userRepository.save(userAccount);
        authorityRepository.save(authority);
        return therapist;
    }

    private Ad addSimpleAd(Therapist therapist, int num) {
        Ad ad = adFactory.createAd(therapist);
        AdDetails details = ad.getDetails();
        details.setPrice("Pierwsza wizyta", 150);
        details.setPrice("Sesja indywidualna", 100);
        details.setName("Imię" + num);
        details.setSurname("Nazwisko" + num);
        details.setAddress("ul. Ulica 1/1 00-000 Miasto");
        details.setOnlineSessions(true);
        therapistRepository.save(therapist);
        adRepository.save(ad);
        return ad;
    }


    private Ad addComplexAd(Therapist therapist, int num) {
        Ad ad = adFactory.createAd(therapist);
        AdDetails details = ad.getDetails();
        details.setPrice("Pierwsza wizyta", 200);
        details.setPrice("Sesja indywidualna", 130);
        details.setName("Imię" + num);
        details.setSurname("Nazwisko" + num);
        details.setAddress("ul. Ulica 1/1 00-000 Miasto");
        details.setTelephoneNumber("123456789");
        details.setEmail("example@email.com");
        details.setTherapyApproach("poznawczo-behawioralny");
        details.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sit amet luctus venenatis lectus magna fringilla urna porttitor rhoncus. Massa tincidunt nunc pulvinar sapien et ligula. Est ultricies integer quis auctor elit sed vulputate mi. Sapien pellentesque habitant morbi tristique senectus et netus. Nisl vel pretium lectus quam id leo. Ut morbi tincidunt augue interdum. Mi quis hendrerit dolor magna eget. Diam maecenas sed enim ut sem viverra aliquet eget sit. Pretium quam vulputate dignissim suspendisse in. Consequat nisl vel pretium lectus quam id leo. Ac turpis egestas maecenas pharetra. Morbi tristique senectus et netus et malesuada fames ac turpis.");
        details.setTherapyCenter("Centrum terapeutyczne X");
        details.setTraining("Certyfikat 1\nCertyfikat 2\nSzkolenie 1");
        details.setSupervision(true);
        details.setOnlineSessions(false);
        therapistRepository.save(therapist);
        adRepository.save(ad);
        return ad;
    }

    private void addComments(Ad ad, int num, Therapist therapist) {
        for (int i=0; i<num; i++) {
            Comment comment = new Comment();
            comment.setAd(ad.getId());
            comment.setAuthor("AutorKomentarza1");
            comment.setContent("TreśćKomentarza1");
            ad.addComment(comment, therapist);
            commentRepository.save(comment);
        }
        adRepository.save(ad);
    }


    @Override
    public void run(String... args) {
        for (int i=1; i<7; i++) {
            Therapist therapist = addTherapist("Test" + i, "pass" + i);
            if (i>2 && i<5) {
                addSimpleAd(therapist, i);
            }
            else if (i>4) {
                Ad ad = addComplexAd(therapist, i);
                if (i>5 ) {
                    addComments(ad, 3, therapist);
                }
            }
        }
        addModerator("admin", "admin");
    }
}
